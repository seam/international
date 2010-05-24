/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
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
 */

package org.jboss.seam.international.status;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;

import org.jboss.seam.international.status.builder.BundleKey;
import org.jboss.seam.international.status.builder.BundleMessage;
import org.jboss.seam.international.status.builder.TemplateMessage;

/**
 * A convenient way to add messages to be displayed to the user as Feedback,
 * Toast, Alerts, etc...
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com>Lincoln Baxter, III</a>
 * 
 */
@SessionScoped
public class Messages implements Serializable
{
   private static final long serialVersionUID = -2908193057765795662L;
   private final Set<Message> messages = Collections.synchronizedSet(new HashSet<Message>());

   @Inject
   MessageFactory factory;

   /**
    * Clear all pending messages.
    */
   public void clear()
   {
      messages.clear();
   }

   /**
    * Retrieve all pending {@link Messages} in their final state - as they will
    * be displayed to the user.
    */
   public Set<Message> getAll()
   {
      Set<Message> result = new HashSet<Message>();
      synchronized (messages)
      {
         result.addAll(messages);
      }
      return result;
   }

   /**
    * Add a {@link Message} object to the pending message cache. Messages remain
    * pending until cleared by and displayed in the View Layer, or until cleared
    * manually by calling {@link #clear()}, or until the user's Session expires.
    * <p>
    * <b>Note:</b> Duplicate messages are ignored.
    */
   public void add(final Message message)
   {
      messages.add(message);
   }

   /**
    * Add a {@link Message} object, produced by the provided
    * {@link MessageBuilder}, to the pending message cache.
    */
   public void add(final MessageBuilder builder)
   {
      messages.add(builder.build());
   }

   /*
    * Bundle Factory Methods
    */
   public BundleMessage info(final BundleKey message)
   {
      return factory.info(message);
   }

   public BundleMessage info(final BundleKey message, final Object... params)
   {
      return factory.info(message, params);
   }

   public BundleMessage warn(final BundleKey message)
   {
      return factory.warn(message);
   }

   public BundleMessage warn(final BundleKey message, final Object... params)
   {
      return factory.warn(message, params);
   }

   public BundleMessage error(final BundleKey message)
   {
      return factory.error(message);
   }

   public BundleMessage error(final BundleKey message, final Object... params)
   {
      return factory.error(message, params);
   }

   public BundleMessage fatal(final BundleKey message)
   {
      return factory.fatal(message);
   }

   public BundleMessage fatal(final BundleKey message, final Object... params)
   {
      return factory.fatal(message, params);
   }

   /*
    * Template Factory Methods
    */
   public TemplateMessage info(final String message)
   {
      return factory.info(message);
   }

   public TemplateMessage info(final String message, final Object... params)
   {
      return factory.info(message, params);
   }

   public TemplateMessage warn(final String message)
   {
      return factory.warn(message);
   }

   public TemplateMessage warn(final String message, final Object... params)
   {
      return factory.warn(message, params);
   }

   public TemplateMessage error(final String message)
   {
      return factory.error(message);
   }

   public TemplateMessage error(final String message, final Object... params)
   {
      return factory.error(message, params);
   }

   public TemplateMessage fatal(final String message)
   {
      return factory.fatal(message);
   }

   public TemplateMessage fatal(final String message, final Object... params)
   {
      return factory.fatal(message, params);
   }

}
