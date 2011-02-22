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

import javax.inject.Inject;

import org.jboss.logging.Logger;
import org.jboss.seam.international.status.Bundles;
import org.jboss.seam.international.status.Level;
import org.jboss.seam.international.status.Message;

/**
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
public class BundleTemplateMessageImpl implements BundleTemplateMessage {
    @Inject
    TemplateMessage template;

    private String textDefault;
    private BundleKey textKey;

    @Inject
    Bundles bundles;

    private final Logger log = Logger.getLogger(BundleTemplateMessageImpl.class);

    public Message build() {
        String text;
        try {
            text = bundles.get(textKey.getBundle()).getString(textKey.getKey());
        } catch (Exception e) {
            log.warn("Could not load bundle: " + textKey);
            text = textDefault;
        }

        if ((text == null) || "".equals(text)) {
            text = textKey.toString();
        }

        template.text(text);
        return template.build();
    }

    /*
     * Setters
     */

    public BundleTemplateMessageImpl key(final BundleKey text) {
        this.textKey = text;
        return this;
    }

    public BundleTemplateMessage defaults(final String text) {
        this.textDefault = text;
        return this;
    }

    public BundleTemplateMessageImpl params(final Object... textParams) {
        this.template.textParams(textParams);
        return this;
    }

    public BundleTemplateMessage targets(final String targets) {
        this.template.targets(targets);
        return this;
    }

    public BundleTemplateMessage level(final Level level) {
        this.template.level(level);
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((template == null) ? 0 : template.hashCode());
        result = prime * result + ((textDefault == null) ? 0 : textDefault.hashCode());
        result = prime * result + ((textKey == null) ? 0 : textKey.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        BundleTemplateMessageImpl other = (BundleTemplateMessageImpl) obj;
        if (template == null) {
            if (other.template != null) {
                return false;
            }
        } else if (!template.equals(other.template)) {
            return false;
        }
        if (textDefault == null) {
            if (other.textDefault != null) {
                return false;
            }
        } else if (!textDefault.equals(other.textDefault)) {
            return false;
        }
        if (textKey == null) {
            if (other.textKey != null) {
                return false;
            }
        } else if (!textKey.equals(other.textKey)) {
            return false;
        }
        return true;
    }
}
