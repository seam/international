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
 * This {@link MessageBuilder} creates {@link Message} objects by interpolating templates with values supplied as parameters.
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
public interface TemplateMessage extends MessageBuilder {
    /**
     * Set the template for this message.
     * <p>
     * Any expressions of the form "{0}, {1} ... {N}" found in the template will be interpolated; numbers reference the index of
     * any given parameters, and can be used more than once per template.
     */
    public TemplateMessage text(final String summary);

    /**
     * Set the parameters for this builder's template.
     * <p>
     * Parameters may be referenced by index in the template, using expressions of the form "{0}, {1} ... {N}"
     */
    public TemplateMessage textParams(final Object... summaryParams);

    /**
     * Set the targets for this message. If supported by the consuming view-layer, these targets may control where/how the
     * message is displayed to the user.
     */
    public TemplateMessage targets(final String targets);

    /**
     * Set the severity, level of importance of this message.
     */
    public TemplateMessage level(final Level level);

}