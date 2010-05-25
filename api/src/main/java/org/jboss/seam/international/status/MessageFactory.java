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

import javax.inject.Inject;

import org.jboss.seam.international.status.builder.BundleKey;
import org.jboss.seam.international.status.builder.BundleMessage;
import org.jboss.seam.international.status.builder.TemplateMessage;

/**
 * A utility for building {@link Message} objects via message templates, or
 * message bundles. See {@link TemplateMessage} or {@link BundleMessage}.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class MessageFactory implements Serializable
{
   private static final long serialVersionUID = -7899463141244189001L;

   @Inject
   private Bundles bundles;

   /*
    * Bundle Factory Methods
    */
   public BundleMessage info(final BundleKey message)
   {
      return new BundleMessage(bundles, Level.INFO).text(message);
   }

   public BundleMessage info(final BundleKey message, final Object... params)
   {
      return new BundleMessage(bundles, Level.INFO).text(message).textParams(params);
   }

   public BundleMessage warn(final BundleKey message)
   {
      return new BundleMessage(bundles, Level.WARN).text(message);
   }

   public BundleMessage warn(final BundleKey message, final Object... params)
   {
      return new BundleMessage(bundles, Level.WARN).text(message).textParams(params);
   }

   public BundleMessage error(final BundleKey message)
   {
      return new BundleMessage(bundles, Level.ERROR).text(message);
   }

   public BundleMessage error(final BundleKey message, final Object... params)
   {
      return new BundleMessage(bundles, Level.ERROR).text(message).textParams(params);
   }

   public BundleMessage fatal(final BundleKey message)
   {
      return new BundleMessage(bundles, Level.FATAL).text(message);
   }

   public BundleMessage fatal(final BundleKey message, final Object... params)
   {
      return new BundleMessage(bundles, Level.FATAL).text(message).textParams(params);
   }

   /*
    * Template Factory Methods
    */
   public TemplateMessage info(final String message)
   {
      return new TemplateMessage(Level.INFO).text(message);
   }

   public TemplateMessage info(final String message, final Object... params)
   {
      return new TemplateMessage(Level.INFO).text(message).textParams(params);
   }

   public TemplateMessage warn(final String message)
   {
      return new TemplateMessage(Level.WARN).text(message);
   }

   public TemplateMessage warn(final String message, final Object... params)
   {
      return new TemplateMessage(Level.WARN).text(message).textParams(params);
   }

   public TemplateMessage error(final String message)
   {
      return new TemplateMessage(Level.ERROR).text(message);
   }

   public TemplateMessage error(final String message, final Object... params)
   {
      return new TemplateMessage(Level.ERROR).text(message).textParams(params);
   }

   public TemplateMessage fatal(final String message)
   {
      return new TemplateMessage(Level.FATAL).text(message);
   }

   public TemplateMessage fatal(final String message, final Object... params)
   {
      return new TemplateMessage(Level.FATAL).text(message).textParams(params);
   }

}
