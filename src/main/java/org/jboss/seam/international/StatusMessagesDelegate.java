package org.jboss.seam.international;

import java.util.List;
import java.util.Map;

import javax.validation.ConstraintViolation;

import org.jboss.seam.international.StatusMessage.Severity;

/**
 * A abstract wrapper class that delegates all operations to the StatusMessages
 * instance. It's up to the implementation to provide access to the
 * StatusMessages instance, preferably obtained through constructor injection.
 * The intent is for this class to be extended to wrap view layer specific
 * functionality around the StatusMessages component.
 * 
 * @author Dan Allen
 */
public abstract class StatusMessagesDelegate extends StatusMessages
{
   public abstract StatusMessages getStatusMessages();

   @Override
   public void onBeforeRender()
   {
      getStatusMessages().onBeforeRender();
   }
   
   @Override
   public void add(ConstraintViolation<?> cv)
   {
      getStatusMessages().add(cv);
   }

   @Override
   public void add(ConstraintViolation<?>[] cvs)
   {
      getStatusMessages().add(cvs);
   }

   @Override
   public void add(Severity severity, String messageTemplate, Object... params)
   {
      getStatusMessages().add(severity, messageTemplate, params);
   }

   @Override
   public void add(Severity severity, String key, String detailKey, String messageTemplate, String messageDetailTemplate, Object... params)
   {
      getStatusMessages().add(severity, key, detailKey, messageTemplate, messageDetailTemplate, params);
   }

   @Override
   public void add(String messageTemplate, Object... params)
   {
      getStatusMessages().add(messageTemplate, params);
   }

   @Override
   public void addFromResourceBundle(Severity severity, String key, Object... params)
   {
      getStatusMessages().addFromResourceBundle(severity, key, params);
   }

   @Override
   public void addFromResourceBundle(String key, Object... params)
   {
      getStatusMessages().addFromResourceBundle(key, params);
   }

   @Override
   public void addFromResourceBundleOrDefault(Severity severity, String key, String defaultMessageTemplate, Object... params)
   {
      getStatusMessages().addFromResourceBundleOrDefault(severity, key, defaultMessageTemplate, params);
   }

   @Override
   public void addFromResourceBundleOrDefault(String key, String defaultMessageTemplate, Object... params)
   {
      getStatusMessages().addFromResourceBundleOrDefault(key, defaultMessageTemplate, params);
   }

   @Override
   public void addToControl(ConstraintViolation<?> cv)
   {
      getStatusMessages().addToControl(cv);
   }

   @Override
   public void addToControl(String id, ConstraintViolation<?> cv)
   {
      getStatusMessages().addToControl(id, cv);
   }

   @Override
   public void addToControl(String id, Severity severity, String messageTemplate, Object... params)
   {
      getStatusMessages().addToControl(id, severity, messageTemplate, params);
   }

   @Override
   public void addToControl(String id, Severity severity, String key, String messageTemplate, Object... params)
   {
      getStatusMessages().addToControl(id, severity, key, messageTemplate, params);
   }

   @Override
   public void addToControl(String id, String messageTemplate, Object... params)
   {
      getStatusMessages().addToControl(id, messageTemplate, params);
   }

   @Override
   public void addToControlFromResourceBundle(String id, Severity severity, String key, Object... params)
   {
      getStatusMessages().addToControlFromResourceBundle(id, severity, key, params);
   }

   @Override
   public void addToControlFromResourceBundle(String id, String key, Object... params)
   {
      getStatusMessages().addToControlFromResourceBundle(id, key, params);
   }

   @Override
   public void addToControlFromResourceBundleOrDefault(String id, Severity severity, String key, String defaultMessageTemplate, Object... params)
   {
      getStatusMessages().addToControlFromResourceBundleOrDefault(id, severity, key, defaultMessageTemplate, params);
   }

   @Override
   public void addToControlFromResourceBundleOrDefault(String id, String key, String defaultMessageTemplate, Object... params)
   {
      getStatusMessages().addToControlFromResourceBundleOrDefault(id, key, defaultMessageTemplate, params);
   }

   @Override
   public void addToControls(ConstraintViolation<?>[] cvs)
   {
      getStatusMessages().addToControls(cvs);
   }

   @Override
   public void clear()
   {
      getStatusMessages().clear();
   }

   @Override
   public void clearGlobalMessages()
   {
      getStatusMessages().clearGlobalMessages();
   }

   @Override
   public void clearKeyedMessages()
   {
      getStatusMessages().clearKeyedMessages();
   }

   @Override
   public void clearKeyedMessages(String id)
   {
      getStatusMessages().clearKeyedMessages(id);
   }

   @Override
   public List<StatusMessage> getGlobalMessages()
   {
      return getStatusMessages().getGlobalMessages();
   }

   @Override
   public Map<String, List<StatusMessage>> getKeyedMessages()
   {
      return getStatusMessages().getKeyedMessages();
   }

   @Override
   public List<StatusMessage> getKeyedMessages(String id)
   {
      return getStatusMessages().getKeyedMessages(id);
   }
}
