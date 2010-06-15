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
import java.util.Set;

import javax.enterprise.context.RequestScoped;

import org.jboss.seam.international.status.builder.BundleKey;
import org.jboss.seam.international.status.builder.BundleTemplateMessage;
import org.jboss.seam.international.status.builder.TemplateMessage;

/**
 * A convenient way to add messages to be displayed to the user as Feedback,
 * Toast, Alerts, etc.
 * <p>
 * It is the responsibility of the view-layer technology to consume and perform
 * operations required to display any messages added in this way.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com>Lincoln Baxter, III</a>
 * 
 */
@RequestScoped
public interface Messages extends Serializable
{
   /**
    * Clear all pending messages.
    */
   public void clear();

   /**
    * Retrieve all pending {@link Messages} in their final state - as they will
    * be displayed to the user. Calling this method will call
    * {@link MessageBuilder#build()} on any queued {@link MessageBuilder}
    * instances, adding the resulting {@link Message} objects to the message
    * cache, and clearing the builders from the builder cache.
    */
   public Set<Message> getAll();

   /**
    * Add a {@link Message} object to the pending message cache. Messages remain
    * pending until cleared by and displayed in the View Layer, or until cleared
    * manually by calling {@link #clear()}, or until the user's Session expires.
    * <p>
    * <b>Note:</b> Duplicate messages are ignored.
    */
   public void add(final Message message);

   /**
    * Add a {@link MessageBuilder} object to the pending builder cache. A
    * subsequent call to {@link Messages#getAll()} will trigger,
    * {@link MessageBuilder#build()} to be called called on each builder added
    * in this way.
    */
   public void add(final MessageBuilder builder);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public BundleTemplateMessage info(final BundleKey message);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public BundleTemplateMessage info(final BundleKey message, final Object... params);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public BundleTemplateMessage warn(final BundleKey message);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public BundleTemplateMessage warn(final BundleKey message, final Object... params);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public BundleTemplateMessage error(final BundleKey message);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public BundleTemplateMessage error(final BundleKey message, final Object... params);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public BundleTemplateMessage fatal(final BundleKey message);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public BundleTemplateMessage fatal(final BundleKey message, final Object... params);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public TemplateMessage info(final String message);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public TemplateMessage info(final String message, final Object... params);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public TemplateMessage warn(final String message);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public TemplateMessage warn(final String message, final Object... params);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public TemplateMessage error(final String message);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public TemplateMessage error(final String message, final Object... params);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public TemplateMessage fatal(final String message);

   /**
    * Create a {@link MessageBuilder} with the specified {@link Level}, add it
    * to the internal queue, and return it.
    */
   public TemplateMessage fatal(final String message, final Object... params);

}
