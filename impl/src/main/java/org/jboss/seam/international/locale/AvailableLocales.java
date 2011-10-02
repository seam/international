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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.jboss.solder.logging.Logger;

@ApplicationScoped
public class AvailableLocales {
    private final Logger log = Logger.getLogger(AvailableLocales.class);

    @Produces
    private List<Locale> locales = null;

    @Inject
    public void init(Instance<LocaleConfiguration> configuration) {
        locales = new ArrayList<Locale>();

        if (!configuration.isAmbiguous() && !configuration.isUnsatisfied()) {
            Set<String> keys = configuration.get().getSupportedLocaleKeys();
            log.trace("Found " + keys.size() + " locales in configuration");
            for (String localeKey : keys) {
                try {
                    Locale lc = LocaleUtils.toLocale(localeKey);
                    locales.add(lc);
                } catch (IllegalArgumentException e) {
                    log.error("AvailableLocales: Supported Locale key of " + localeKey + " was not formatted correctly", e);
                }
            }
        }

        Collections.sort(locales, new Comparator<Locale>() {
            public int compare(final Locale a, final Locale b) {
                return a.toString().compareTo(b.toString());
            }
        });

        locales = Collections.unmodifiableList(locales);
    }
}
