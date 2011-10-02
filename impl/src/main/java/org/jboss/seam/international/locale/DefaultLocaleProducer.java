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
package org.jboss.seam.international.locale;

import java.io.Serializable;
import java.util.Locale;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.solder.logging.Logger;

@ApplicationScoped
public class DefaultLocaleProducer implements Serializable {
    private static final long serialVersionUID = -4534087316489937649L;

    private Logger log = Logger.getLogger(DefaultLocaleProducer.class);

    @Inject
    @DefaultLocale
    private Instance<String> defaultLocaleKey;

    @Produces
    @Named
    private Locale defaultLocale;

    @PostConstruct
    public void init() {
        if (!defaultLocaleKey.isUnsatisfied()) {
            try {
                String key = defaultLocaleKey.get();
                defaultLocale = LocaleUtils.toLocale(key);
            } catch (IllegalArgumentException e) {
                log.error("DefaultLocaleProducer: Default Locale key of " + defaultLocale + " was not formatted correctly", e);
            }
        }
        if (null == defaultLocale) {
            defaultLocale = Locale.getDefault();
        }
    }
}
