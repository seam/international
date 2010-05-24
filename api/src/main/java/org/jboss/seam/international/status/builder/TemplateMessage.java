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
package org.jboss.seam.international.status.builder;

import org.jboss.seam.international.status.Level;
import org.jboss.seam.international.status.Message;
import org.jboss.seam.international.status.MessageBuilder;
import org.jboss.seam.international.status.MutableMessage;

/**
 * This {@link MessageBuilder} implementation creates {@link Message} objects by
 * interpolating templates with values supplied as parameters.
 * <p>
 * <b>For example:</b> Given the following {@link Message} m
 * 
 * <pre>
 * Message m = {@link MessageFactory}.info(&quot;There are {0} cars, and they are all {1}.&quot;, 5, &quot;green&quot;).build();
 * </pre>
 * 
 * A subsequent call to <code>m.getSummary()</code> will return:<br/>
 * <code>"There are 5 cars, and they are all green."</code>;
 * <p>
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class TemplateMessage implements MessageBuilder
{
   private static final String MESSAGE_IMPL_CLASS = "org.jboss.seam.international.status.MessageImpl";

   private final Interpolator templater = new Interpolator();

   private String summary;
   private Object[] summaryParams;

   private String targets;
   private Level level;

   public TemplateMessage(final Level level)
   {
      this.level = level;
   }

   /**
    * Produce a {@link Message} object as represented by the current state of
    * <code>this</code> builder.
    */
   public Message build()
   {
      Class<?> type;
      try
      {
         type = Class.forName(MESSAGE_IMPL_CLASS);
         MutableMessage message = (MutableMessage) type.newInstance();

         message.setLevel(level);
         message.setText(templater.populate(summary, summaryParams));
         message.setTargets(targets);

         return message;
      }
      catch (Exception e)
      {
         throw new RuntimeException("Unable to create message: " + MESSAGE_IMPL_CLASS, e);
      }
   }

   /*
    * Setters
    */
   public TemplateMessage text(final String summary)
   {
      this.summary = summary;
      return this;
   }

   public TemplateMessage textParams(final Object... summaryParams)
   {
      this.summaryParams = summaryParams;
      return this;
   }

   public TemplateMessage targets(final String targets)
   {
      this.targets = targets;
      return this;
   }

   public TemplateMessage level(final Level level)
   {
      this.level = level;
      return this;
   }
}
