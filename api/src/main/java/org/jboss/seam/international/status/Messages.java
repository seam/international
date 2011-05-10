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
import java.util.Set;

import javax.enterprise.context.RequestScoped;

import org.jboss.seam.international.status.builder.BundleKey;
import org.jboss.seam.international.status.builder.BundleTemplateMessage;
import org.jboss.seam.international.status.builder.TemplateMessage;

/**
 * A convenient way to add messages to be displayed to the user as Feedback, Toast, Alerts, etc.
 * <p/>
 * It is the responsibility of the view-layer technology to consume and perform operations required to display any messages
 * added in this way.
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com>Lincoln Baxter, III</a>
 */
@RequestScoped
public interface Messages extends Serializable {
    /**
     * Clear all pending messages.
     */
    public void clear();

    /**
     * Return true if there are no pending {@link Message} or {@link MessageBuilder} objects in the queue.
     */
    public boolean isEmpty();

    /**
     * Retrieve all pending {@link Messages} in their final state - as they will be displayed to the user. Calling this method
     * will call {@link MessageBuilder#build()} on any queued {@link MessageBuilder} instances, adding the resulting
     * {@link Message} objects to the message cache, and clearing the builders from the builder cache.
     */
    public Set<Message> getAll();

    /**
     * Add a {@link Message} object to the pending message cache. Messages remain pending until cleared by and displayed in the
     * View Layer, or until cleared manually by calling {@link #clear()}, or until the user's Session expires.
     * <p/>
     * <b>Note:</b> Duplicate messages are ignored.
     */
    public void add(final Message message);

    /**
     * Add a {@link MessageBuilder} object to the pending builder cache. A subsequent call to {@link Messages#getAll()} will
     * trigger, {@link MessageBuilder#build()} to be called called on each builder added in this way.
     */
    public void add(final MessageBuilder builder);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public BundleTemplateMessage info(final BundleKey message);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public BundleTemplateMessage info(final BundleKey message, final Object... params);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public BundleTemplateMessage warn(final BundleKey message);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public BundleTemplateMessage warn(final BundleKey message, final Object... params);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public BundleTemplateMessage error(final BundleKey message);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public BundleTemplateMessage error(final BundleKey message, final Object... params);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public BundleTemplateMessage fatal(final BundleKey message);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public BundleTemplateMessage fatal(final BundleKey message, final Object... params);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public TemplateMessage info(final String message);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public TemplateMessage info(final String message, final Object... params);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public TemplateMessage warn(final String message);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public TemplateMessage warn(final String message, final Object... params);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public TemplateMessage error(final String message);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public TemplateMessage error(final String message, final Object... params);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public TemplateMessage fatal(final String message);

    /**
     * Create a {@link MessageBuilder} with the specified {@link Level}, add it to the internal queue, and return it.
     */
    public TemplateMessage fatal(final String message, final Object... params);

}
