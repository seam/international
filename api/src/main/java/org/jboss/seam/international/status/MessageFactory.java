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
package org.jboss.seam.international.status;

import java.io.Serializable;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.jboss.seam.international.status.builder.BundleKey;
import org.jboss.seam.international.status.builder.BundleTemplateMessage;
import org.jboss.seam.international.status.builder.TemplateMessage;

/**
 * A utility for building {@link Message} objects via message templates, or message bundles. See {@link TemplateMessage} or
 * {@link BundleTemplateMessage} .
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
public class MessageFactory implements Serializable {
    private static final long serialVersionUID = -7899463141244189001L;

    @Inject
    BeanManager manager;

    /*
     * Bundle Factory Methods
     */
    public BundleTemplateMessage info(final BundleKey message) {
        return getContextualInstance(BundleTemplateMessage.class).key(message).level(Level.INFO);
    }

    public BundleTemplateMessage info(final BundleKey message, final Object... params) {
        return getContextualInstance(BundleTemplateMessage.class).key(message).level(Level.INFO).params(params);
    }

    public BundleTemplateMessage warn(final BundleKey message) {
        return getContextualInstance(BundleTemplateMessage.class).key(message).level(Level.WARN);
    }

    public BundleTemplateMessage warn(final BundleKey message, final Object... params) {
        return getContextualInstance(BundleTemplateMessage.class).key(message).level(Level.WARN).params(params);
    }

    public BundleTemplateMessage error(final BundleKey message) {
        return getContextualInstance(BundleTemplateMessage.class).key(message).level(Level.ERROR);
    }

    public BundleTemplateMessage error(final BundleKey message, final Object... params) {
        return getContextualInstance(BundleTemplateMessage.class).key(message).level(Level.ERROR).params(params);
    }

    public BundleTemplateMessage fatal(final BundleKey message) {
        return getContextualInstance(BundleTemplateMessage.class).key(message).level(Level.FATAL);
    }

    public BundleTemplateMessage fatal(final BundleKey message, final Object... params) {
        return getContextualInstance(BundleTemplateMessage.class).key(message).level(Level.FATAL).params(params);
    }

    /*
     * Template Factory Methods
     */
    public TemplateMessage info(final String message) {
        return getContextualInstance(TemplateMessage.class).text(message).level(Level.INFO);
    }

    public TemplateMessage info(final String message, final Object... params) {
        return getContextualInstance(TemplateMessage.class).text(message).level(Level.INFO).textParams(params);
    }

    public TemplateMessage warn(final String message) {
        return getContextualInstance(TemplateMessage.class).text(message).level(Level.WARN);
    }

    public TemplateMessage warn(final String message, final Object... params) {
        return getContextualInstance(TemplateMessage.class).text(message).level(Level.WARN).textParams(params);
    }

    public TemplateMessage error(final String message) {
        return getContextualInstance(TemplateMessage.class).text(message).level(Level.ERROR);
    }

    public TemplateMessage error(final String message, final Object... params) {
        return getContextualInstance(TemplateMessage.class).text(message).level(Level.ERROR).textParams(params);
    }

    public TemplateMessage fatal(final String message) {
        return getContextualInstance(TemplateMessage.class).text(message).level(Level.FATAL);
    }

    public TemplateMessage fatal(final String message, final Object... params) {
        return getContextualInstance(TemplateMessage.class).text(message).level(Level.FATAL).textParams(params);
    }

    /**
     * Get a single CDI managed instance of a specific class. Return only the first result if multiple beans are available.
     * <p/>
     * <b>NOTE:</b> Using this method should be avoided at all costs.
     *
     * @param manager The bean manager with which to perform the lookup.
     * @param type    The class for which to return an instance.
     * @return The managed instance, or null if none could be provided.
     */
    @SuppressWarnings("unchecked")
    private <T extends MessageBuilder> T getContextualInstance(final Class<T> type) {
        T result = null;
        Bean<T> bean = (Bean<T>) manager.resolve(manager.getBeans(type));
        if (bean != null) {
            CreationalContext<T> context = manager.createCreationalContext(bean);
            if (context != null) {
                result = (T) manager.getReference(bean, type, context);
            }
        }
        return result;
    }

}
