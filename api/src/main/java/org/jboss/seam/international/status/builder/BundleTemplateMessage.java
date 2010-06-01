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
public interface BundleTemplateMessage extends MessageBuilder
{
   /**
    * Set the template for this message, using the given {@link BundleKey} to
    * perform a resource lookup.
    * <p>
    * Any expressions of the form "{0}, {1} ... {N}" found in the template will
    * be interpolated; numbers reference the index of any given parameters, and
    * can be used more than once per template.
    */
   public BundleTemplateMessage text(final BundleKey text);

   /**
    * Set the default template text.
    * <p>
    * If the bundle cannot be loaded for any reason, the builder will fall back
    * to using provided default template text; if there is no default template,
    * a string representation of the {@link BundleKey} will be used instead.
    * <p>
    * Any expressions of the form "{0}, {1} ... {N}" found in the template will
    * be interpolated; numbers reference the index of any given parameters, and
    * can be used more than once per template.
    */
   public BundleTemplateMessage textDefault(final String text);

   /**
    * Set the parameters for this builder's template.
    * <p>
    * Parameters may be referenced by index in the template or
    * {@link #textDefault(String)}, using expressions of the form " {0}, {1} ...
    * {N}". The same parameters will be used when interpolating default text, in
    * the case when a {@link BundleKey} cannot be resolved.
    */
   public BundleTemplateMessage textParams(final Object... textParams);

   /**
    * Set the targets for this message. If supported by the consuming
    * view-layer, these targets may control where/how the message is displayed
    * to the user.
    */
   public BundleTemplateMessage targets(final String targets);

   /**
    * Set the severity, level of importance of this message.
    */
   public BundleTemplateMessage level(final Level level);

}