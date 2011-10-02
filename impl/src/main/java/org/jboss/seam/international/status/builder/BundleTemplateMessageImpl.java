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

import java.util.Locale;

import javax.inject.Inject;

import org.jboss.solder.logging.Logger;
import org.jboss.seam.international.status.ApplicationBundles;
import org.jboss.seam.international.status.Level;
import org.jboss.seam.international.status.Message;
import org.jboss.solder.core.Client;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * @author <a href="mailto:ssachtleben@gmail.com">Sebastian Sachtleben</a>
 */
public class BundleTemplateMessageImpl implements BundleTemplateMessage {
    @Inject
    TemplateMessage template;

    private String textDefault;
    private BundleKey textKey;
    private BundleKey detailKey;

    @Inject
    ApplicationBundles bundles;

    @Inject
    @Client
    Locale clientLocale;

    private final Logger log = Logger.getLogger(BundleTemplateMessageImpl.class);

    public Message build() {
        String text;
        String detail = null;
        try {
            text = bundles.get(clientLocale, textKey.getBundle()).getString(textKey.getKey());
            if (detailKey != null) {
                detail = bundles.get(clientLocale, detailKey.getBundle()).getString(detailKey.getKey());
            }
        } catch (Exception e) {
            log.warn("Could not load bundle: " + textKey);
            text = textDefault;
        }

        if ((text == null) || "".equals(text)) {
            text = textKey.toString();
        }

        template.text(text);
        if (detail != null) {
            template.detail(detail);
        }
        return template.build();
    }

    /*
     * Setters
     */

    public BundleTemplateMessageImpl key(final BundleKey text) {
        this.textKey = text;
        return this;
    }

    public BundleTemplateMessageImpl detail(final BundleKey detailKey) {
        this.detailKey = detailKey;
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

    public BundleTemplateMessageImpl detailParams(final Object... detailParams) {
        this.template.detailParams(detailParams);
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
        result = prime * result + ((detailKey == null) ? 0 : detailKey.hashCode());
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
        if (detailKey == null) {
            if (other.detailKey != null) {
                return false;
            }
        } else if (!detailKey.equals(other.detailKey)) {
            return false;
        }
        return true;
    }
}
