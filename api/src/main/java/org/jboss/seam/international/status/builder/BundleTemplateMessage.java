/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.international.status.builder;

import org.jboss.seam.international.status.Level;
import org.jboss.seam.international.status.Message;
import org.jboss.seam.international.status.MessageBuilder;

/**
 * This {@link MessageBuilder} implementation creates {@link Message} objects by loading resource bundle keys as templates with
 * values supplied as parameters.
 * <p/>
 * <b>For example:</b>
 * <p/>
 * Given the following {@link Message} m
 * <p/>
 * <pre>
 * Message m = {@link MessageFactory}.info(new {@link BundleKey}(&quot;messageBundle&quot;, &quot;keyName&quot;), 5, &quot;green&quot;)
 * &nbsp;&nbsp;&nbsp;.defaultText("This is default text.").build();
 * </pre>
 * <p/>
 * And the corresponding messageBundle.properties file:<br>
 * <p/>
 * <pre>
 * keyName=There are {0} cars, and they are all {1}.
 * </pre>
 * <p/>
 * A subsequent call to <code>m.getText()</code> will return:<br/>
 * <p/>
 * <pre>
 * &quot;There are 5 cars, and they are all green.&quot;
 * </pre>
 * <p/>
 * <b>Note:</b> If a bundle/key pair cannot be resolved, the default template will be used instead. If there is no default
 * template, a String representation of the {@link BundleKey} will be displayed instead.
 * <p/>
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * @author <a href="mailto:ssachtleben@gmail.com">Sebastian Sachtleben</a>
 */
public interface BundleTemplateMessage extends MessageBuilder {
    /**
     * Use the given {@link BundleKey} to perform a resource lookup, resolving the template to render for this message.
     * <p/>
     * Any expressions of the form "{0}, {1} ... {N}" found in the template will be interpolated; numbers reference the index of
     * any given parameters, and can be used more than once per template.
     */
    public BundleTemplateMessage key(final BundleKey text);

    /**
     * Use the given {@link BundleKey} to perform a resource lookup, resolving the template to render detail text for this message.
     * <p/>
     * Any expressions of the form "{0}, {1} ... {N}" found in the template will be interpolated; numbers reference the index of
     * any given parameters, and can be used more than once per template.
     */
    public BundleTemplateMessage detail(final BundleKey detail);

    /**
     * Set the default template text.
     * <p/>
     * If the bundle cannot be loaded for any reason, the builder will fall back to using provided default template text; if
     * there is no default template, a string representation of the {@link BundleKey} will be used instead.
     * <p/>
     * Any expressions of the form "{0}, {1} ... {N}" found in the template will be interpolated; numbers reference the index of
     * any given parameters, and can be used more than once per template.
     */
    public BundleTemplateMessage defaults(final String text);

    /**
     * Set the parameters for this builder's template.
     * <p/>
     * Parameters may be referenced by index in the template or {@link #textDefault(String)}, using expressions of the form "
     * {0}, {1} ... {N}". The same parameters will be used when interpolating default text, in the case when a {@link BundleKey}
     * cannot be resolved.
     */
    public BundleTemplateMessage params(final Object... textParams);

    /**
     * Set the parameters for detail text of this builder's template.
     * <p/>
     * Parameters may be referenced by index in the template or {@link #textDefault(String)}, using expressions of the form "
     * {0}, {1} ... {N}". The same parameters will be used when interpolating default text, in the case when a {@link BundleKey}
     * cannot be resolved.
     */
    public BundleTemplateMessage detailParams(final Object... detailParams);

    /**
     * Set the targets for this message. If supported by the consuming view-layer, these targets may control where/how the
     * message is displayed to the user.
     */
    public BundleTemplateMessage targets(final String targets);

    /**
     * Set the severity, level of importance of this message.
     */
    public BundleTemplateMessage level(final Level level);

}
