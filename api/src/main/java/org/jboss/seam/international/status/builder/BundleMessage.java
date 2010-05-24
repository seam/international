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

import org.jboss.seam.international.status.Bundles;
import org.jboss.seam.international.status.Level;
import org.jboss.seam.international.status.Message;
import org.jboss.seam.international.status.MessageBuilder;

/**
 * This {@link MessageBuilder} implementation creates {@link Message} objects by
 * loading resource bundle keys as templates with values supplied as parameters.
 * <p>
 * <b>For example:</b>
 * <p>
 * Given the following {@link Message} m
 * 
 * <pre>
 * Message m = {@link MessageFactory}.info(new {@link BundleKey}(&quot;messageBundle&quot;, &quot;keyName&quot;), 5, &quot;green&quot;)
 * &nbsp;&nbsp;&nbsp;.defaultText("This is default text.").build();
 * </pre>
 * 
 * And the corresponding messageBundle.properties file:<br>
 * 
 * <pre>
 * keyName=There are {0} cars, and they are all {1}.
 * </pre>
 * 
 * A subsequent call to <code>m.getText()</code> will return:<br/>
 * 
 * <pre>
 * &quot;There are 5 cars, and they are all green.&quot;
 * </pre>
 * 
 * <b>Note:</b> If a bundle/key pair cannot be resolved, the default template
 * will be used instead. If there is no default template, a String
 * representation of the {@link BundleKey} will be displayed instead.
 * <p>
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class BundleMessage implements MessageBuilder
{
   private TemplateMessage template;
   private String textDefault;
   private BundleKey textKey;

   private final Bundles bundles;

   public BundleMessage(final Bundles bundles, final Level level)
   {
      this.bundles = bundles;
      this.template.level(level);
   }

   /**
    * Produce a {@link Message} object as represented by the current state of
    * <code>this</code> builder.
    */
   public Message build()
   {
      String text;
      try
      {
         text = bundles.get(textKey.getBundle()).getString(textKey.getKey());
      }
      catch (Exception e)
      {
         text = textDefault;
      }

      if ((text == null) || "".equals(text))
      {
         text = textKey.toString();
      }

      template.text(text);
      return template.build();
   }

   /*
    * Setters
    */

   public BundleMessage text(final BundleKey text)
   {
      this.textKey = text;
      return this;
   }

   public BundleMessage textDefault(final String text)
   {
      this.textDefault = text;
      return this;
   }

   public BundleMessage textParams(final Object... textParams)
   {
      this.template.textParams(textParams);
      return this;
   }

   public BundleMessage targets(final String targets)
   {
      this.template.targets(targets);
      return this;
   }

   public BundleMessage setLevel(final Level level)
   {
      this.template.level(level);
      return this;
   }
}
