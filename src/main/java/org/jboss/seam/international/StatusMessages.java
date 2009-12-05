/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 * $Id$
 */
package org.jboss.seam.international;

import static org.jboss.seam.international.StatusMessage.Severity.INFO;
import static org.jboss.seam.international.StatusMessage.Severity.WARN;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.enterprise.context.ConversationScoped;
import javax.enterprise.inject.AnnotationLiteral;
import javax.inject.Inject;
import javax.inject.Named;
import javax.enterprise.inject.spi.BeanManager;
import javax.validation.ConstraintViolation;

import org.jboss.seam.beans.BeanManagerHelper;
import org.jboss.seam.international.StatusMessage.Severity;
import org.jboss.webbeans.log.Log;
import org.jboss.webbeans.log.Logger;

/**
 * <p>{@link StatusMessages} is a technology agnostic repository for holding
 * status messages intended to be displayed to an end user. View layer modules
 * should provide a concrete implementation that handles tranposing the generic
 * status messages into status messages specific to the view techology (e.g.,
 * JSF, Wicket).</p>
 *
 * <p>A status message can either be global or it can be keyed to a "client id", which
 * is the id of a cooresponding user interface component on the page. The default
 * severity of a global message is INFO, whereas the default severity of a message
 * added to a control is WARN. These defaults are choosen since they represent the
 * most common usage scenario.</p>
 *
 * <p>Seam will interpolate value expressions and positional parameters in
 * message templates. Interpolation is deferred until just before the next view
 * in the conversation is rendered, allowing value expressions to resolve to
 * the final state of the action as does the rest of the view.</p>
 * 
 * @author Pete Muir
 * @author Dan Allen
 * @see StatusMessage
 */
public
//@Named("org.jboss.seam.international.statusMessages")
@ConversationScoped
class StatusMessages implements Serializable
{
   private static final long serialVersionUID = -5396789975397138270L;

   @Logger Log log;
   
   private List<StatusMessage> messages = new ArrayList<StatusMessage>();

   private Map<String, List<StatusMessage>> keyedMessages = new HashMap<String, List<StatusMessage>>();

   private transient List<Runnable> tasks;

   private Interpolator interpolator;
   
   // FIXME the scoping may not be right unless resourceBundle updates internal (for instance per request)
   //private ResourceBundle resourceBundle;
   @Inject BeanManager manager;

   public StatusMessages() {}

   public @Inject StatusMessages(Interpolator interpolator) //, @Messages ResourceBundle resourceBundle)
   {
      this.interpolator = interpolator;
      //this.resourceBundle = resourceBundle;
   }
   
   /**
    * Get a list of StatusMessage objects that are not associated with a "client id".
    *
    * @return A list of StatusMessage objects not associated with a "client id"
    */
   public List<StatusMessage> getGlobalMessages()
   {
      return messages;
   }
   
   /**
    * Get a list of StatusMessage objects that are associated with the given "client id".
    * 
    * @param id The "client id" of the user interface component to which the messages apply
    * 
    * @return A list of StatusMessage objects associated with the given "client id"
    */
   public List<StatusMessage> getKeyedMessages(String id)
   {
      return keyedMessages.get(id);
   }

   /**
    * Get a map of StatusMessage objects associated with a "client id". The keys in the map are
    * the "client id" values and the values for each key are the messages.
    *
    * @return A map of StatusMessage objects keyed by "client id"
    */
   public Map<String, List<StatusMessage>> getKeyedMessages()
   {
      return keyedMessages;
   }

   /**
    * Clear all status messages.
    */
   public void clear()
   {
      messages.clear();
      keyedMessages.clear();
   }

   /**
    * Clear all status messages associated with a "client id".
    */
   public void clearKeyedMessages()
   {
      keyedMessages.clear();
   }

   /**
    * Clear all status messages associated with a given "client id".
    *
    * @param id The "client id" of the user interface component to which the messages apply
    */
   public void clearKeyedMessages(String id)
   {
      keyedMessages.remove(id);
   }

   /**
    * Clear all status messages not associated with a "client id".
    */
   public void clearGlobalMessages()
   {
      messages.clear();
   }

   /**
    * Add a status message, looking up the message in the resource bundle
    * using the provided key. If the message is found, it is used, otherwise,
    * the defaultMessageTemplate will be used.
    * 
    * You can also specify the severity, and parameters to be interpolated
    */
   public void add(Severity severity, String summaryKey, String detailKey, String messageSummaryTemplate, String messageDetailTemplate, final Object... params)
   {
      final StatusMessage message = new StatusMessage(severity, getBundleMessage(summaryKey, messageSummaryTemplate), getBundleMessage(detailKey, messageDetailTemplate));
      if (!message.isEmpty())
      {
         messages.add(message);
         getTasks().add(new InterpolateMessageTask(message, params));
      }
   }

   /**
    * Add a status message, looking up the message in the resource bundle
    * using the provided key. If the message is found, it is used, otherwise,
    * the defaultMessageTemplate will be used.
    * 
    * The message will be added to the widget specified by the ID. The algorithm
    * used determine which widget the id refers to is determined by the view
    * layer implementation in use.
    * 
    * You can also specify the severity, and parameters to be interpolated
    */
   public void addToControl(String id, Severity severity, String key, String messageTemplate, final Object... params)
   {
      final StatusMessage message = new StatusMessage(severity, getBundleMessage(key, messageTemplate));
      if (!message.isEmpty())
      {
         if (keyedMessages.containsKey(id))
         {
            keyedMessages.get(id).add(message);
         }
         else
         {
            List<StatusMessage> list = new ArrayList<StatusMessage>();
            list.add(message);
            keyedMessages.put(id, list);
         }
         getTasks().add(new InterpolateMessageTask(message, params));
      }
   }

   /**
    * Create a new status message, with the messageTemplate is as the message.
    *
    * A severity of INFO will be used, and you can specify paramters to be
    * interpolated
    */
   public void add(String messageTemplate, Object... params)
   {
      add(INFO, messageTemplate, params);
   }

   /**
    * Create a new status message, with the messageTemplate is as the message.
    * 
    * You can also specify the severity, and parameters to be interpolated
    */
   public void add(Severity severity, String messageTemplate, Object... params)
   {
      add(severity, null, null, messageTemplate, null, params);
   }

   /**
    * Create a new status message, with the messageTemplate is as the message.
    * 
    * The message will be added to the widget specified by the ID. The algorithm
    * used determine which widget the id refers to is determined by the view
    * layer implementation in use.
    * 
    * A severity of WARN will be used, and you can specify parameters to be
    * interpolated
    */
   public void addToControl(String id, String messageTemplate, Object... params)
   {
      addToControl(id, WARN, null, messageTemplate, params);
   }

   /**
    * Create a new status message, with the messageTemplate is as the message.
    * 
    * The message will be added to the widget specified by the ID. The algorithm
    * used determine which widget the id refers to is determined by the view
    * layer implementation in use.
    * 
    * You can also specify the severity, and parameters to be interpolated
    * 
    */
   public void addToControl(String id, Severity severity, String messageTemplate, Object... params)
   {
      addToControl(id, severity, null, messageTemplate, params);
   }

   /**
    * Add a status message, looking up the message in the resource bundle
    * using the provided key. If the message is found, it is used, otherwise,
    * the defaultMessageTemplate will be used.
    * 
    * A severity of INFO will be used, and you can specify parameters to be
    * interpolated
    */
   public void addFromResourceBundle(String key, Object... params)
   {
      addFromResourceBundle(INFO, key, params);
   }

   /**
    * Add a status message, looking up the message in the resource bundle
    * using the provided key.
    * 
    * You can also specify the severity, and parameters to be interpolated
    */
   public void addFromResourceBundle(Severity severity, String key, Object... params)
   {
      addFromResourceBundleOrDefault(severity, key, key, params);
   }

   /**
    * Add a status message, looking up the message in the resource bundle
    * using the provided key. If the message is found, it is used, otherwise,
    * the defaultMessageTemplate will be used.
    * 
    * A severity of INFO will be used, and you can specify parameters to be
    * interpolated
    */
   public void addFromResourceBundleOrDefault(String key, String defaultMessageTemplate, Object... params)
   {
      addFromResourceBundleOrDefault(INFO, key, defaultMessageTemplate, params);
   }

   /**
    * Add a status message, looking up the message in the resource bundle
    * using the provided key. If the message is found, it is used, otherwise,
    * the defaultMessageTemplate will be used.
    * 
    * You can also specify the severity, and parameters to be interpolated
    */
   public void addFromResourceBundleOrDefault(Severity severity, String key, String defaultMessageTemplate, Object... params)
   {
      add(severity, key, null, defaultMessageTemplate, null, params);
   }

   /**
    * Create a new status message, looking up the message in the resource bundle
    * using the provided key.
    * 
    * The message will be added to the widget specified by the ID. The algorithm
    * used determine which widget the id refers to is determined by the view
    * layer implementation in use.
    * 
    * A severity of WARN will be used, and you can specify parameters to be
    * interpolated
    */
   public void addToControlFromResourceBundle(String id, String key, Object... params)
   {
      addToControlFromResourceBundle(id, WARN, key, params);
   }

   /**
    * Create a new status message, looking up the message in the resource bundle
    * using the provided key.
    * 
    * The message will be added to the widget specified by the ID. The algorithm
    * used determine which widget the id refers to is determined by the view
    * layer implementation in use.
    * 
    * You can also specify the severity, and parameters to be interpolated
    */
   public void addToControlFromResourceBundle(String id, Severity severity, String key, Object... params)
   {
      addToControlFromResourceBundleOrDefault(id, severity, key, key, params);
   }

   /**
    * Add a status message, looking up the message in the resource bundle
    * using the provided key. If the message is found, it is used, otherwise,
    * the defaultMessageTemplate will be used.
    * 
    * The message will be added to the widget specified by the ID. The algorithm
    * used determine which widget the id refers to is determined by the view
    * layer implementation in use.
    * 
    * A severity of WARN will be used, and you can specify parameters to be
    * interpolated
    */
   public void addToControlFromResourceBundleOrDefault(String id, String key, String defaultMessageTemplate, Object... params)
   {
      addToControlFromResourceBundleOrDefault(id, WARN, key, defaultMessageTemplate, params);
   }

   /**
    * Add a status message, looking up the message in the resource bundle
    * using the provided key. If the message is found, it is used, otherwise,
    * the defaultMessageTemplate will be used.
    * 
    * The message will be added to the widget specified by the ID. The algorithm
    * used determine which widget the id refers to is determined by the view
    * layer implementation in use.
    * 
    * You can also specify the severity, and parameters to be interpolated
    */
   public void addToControlFromResourceBundleOrDefault(String id, Severity severity, String key, String defaultMessageTemplate, Object... params)
   {
      addToControl(id, severity, key, defaultMessageTemplate, params);
   }

   /**
    * Add an array of {@link ConstraintViolation} objects from Bean Validation. Each message will
    * be added with a severity of WARN.
    */
   public void add(ConstraintViolation<?>[] cvs)
   {
      for (ConstraintViolation<?> cv : cvs)
      {
         add(cv);
      }
   }

   /**
    * Add an array of {@link ConstraintViolation} objects from Bean Validation. Each message will
    * be added with a severity of WARN.
    * 
    * The name of the property that was validated will be used as the widget ID
    */
   public void addToControls(ConstraintViolation<?>[] cvs)
   {
      for (ConstraintViolation<?> cv : cvs)
      {
         addToControl(cv);
      }
   }

   /**
    * Add a {@link ConstraintViolation} from Bean Validation. The message will
    * be added with a severity of WARN.
    */
   public void add(ConstraintViolation<?> cv)
   {
      add(WARN, cv.getMessage());
   }

   /**
    * Add a {@link ConstraintViolation} from Bean Validation. The message will
    * be added with a severity of WARN.
    * 
    * The name of the property that was validated will be used as the widget ID
    */
   public void addToControl(ConstraintViolation<?> cv)
   {
      String propertyName = cv.getPropertyPath().substring(cv.getPropertyPath().lastIndexOf(".") + 1);
      addToControl(propertyName, cv);
   }

   /**
    * Add a {@link ConstraintViolation} from Bean Validation. The message will
    * be added with a severity of WARN.
    * 
    * You can also specify the id of the widget to add the message to
    */
   public void addToControl(String id, ConstraintViolation<?> cv)
   {
      addToControl(id, WARN, cv.getMessage());
   }

   /**
    * Run the tasks on each message that interpolate the message templates. View layer
    * modules are expected to invoke this message. The view layer implementation may override
    * this method to transpose the messages into the view layer-specific messages.
    */
   public void onBeforeRender()
   {
      doRunTasks();
   }

   protected String getBundleMessage(String key)
   {
      return getBundleMessage(key, key);
   }
   
   protected String getBundleMessage(String key, String defaultMessageTemplate)
   {
      String messageTemplate = defaultMessageTemplate;
      if (key != null)
      {
         // FIXME this is a hack because I can't inject it since ResourceBundle is not proxyable
         if (manager.getBeans(ResourceBundle.class, new AnnotationLiteral<Messages>() {}).size() == 1)
         {
            ResourceBundle resourceBundle = 
                BeanManagerHelper.getInstanceByType(manager, 
                                                    ResourceBundle.class, 
                                                    new AnnotationLiteral<Messages>() {});
            try
            {
               String bundleMessage = resourceBundle.getString(key);
               if (bundleMessage != null)
               {
                  messageTemplate = bundleMessage;
               }
            }
            catch (MissingResourceException mre)
            {
               log.trace("Message key " + key + " was not defined in the Seam resource bundle. Using default message template");
            }
         }
      }
      return messageTemplate;
   }
   
   private List<Runnable> getTasks()
   {
      if (tasks == null)
      {
         tasks = new ArrayList<Runnable>();
      }
      return tasks;
   }

   protected void doRunTasks()
   {
      if (tasks != null)
      {
         for (Runnable task : tasks)
         {
            task.run();
         }
         tasks.clear();
      }
   }

   private class InterpolateMessageTask implements Runnable
   {
      private final StatusMessage message;
      private final Object[] params;

      public InterpolateMessageTask(StatusMessage message, Object[] params)
      {
         this.message = message;
         this.params = params;
      }

      public void run()
      {
         message.interpolate(interpolator, params);
      }
   }
}
