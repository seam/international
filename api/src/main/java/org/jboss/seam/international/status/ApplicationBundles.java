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
import java.util.Collection;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

/**
 * Maintains a global map of locales containing {@link ResourceBundle} objects.
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * @author <a href="http://community.jboss.org/people/ssachtleben">Sebastian Sachtleben</a>
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
@ApplicationScoped
public class ApplicationBundles implements Serializable {
    private static final long serialVersionUID = 1207758648760266247L;

    private final Map<Locale, Map<String, ResourceBundle>> bundles = new ConcurrentHashMap<Locale, Map<String, ResourceBundle>>();

    @Inject
    Locale appLocale;

    public ApplicationBundles() {
    }

    public void clear(final Locale locale) {
        containsLocaleMap(locale);
        bundles.get(locale).clear();
    }

    public boolean containsKey(final Locale locale, final Object key) {
        containsLocaleMap(locale);
        return bundles.get(locale).containsKey(key);
    }

    public boolean containsValue(final Locale locale, final Object value) {
        containsLocaleMap(locale);
        return bundles.get(locale).containsValue(value);
    }

    public Set<java.util.Map.Entry<String, ResourceBundle>> entrySet(final Locale locale) {
        containsLocaleMap(locale);
        return bundles.get(locale).entrySet();
    }

    public ResourceBundle get(final Object key) {
        return get(appLocale, key);
    }

    public ResourceBundle get(final Locale locale, final Object key) {
        containsLocaleMap(locale);
        if (!bundles.get(locale).containsKey(key)) {
            ResourceBundle bundle = ResourceBundle.getBundle(key.toString(), locale);
            put(locale, key.toString(), bundle);
        }
        return bundles.get(locale).get(key);
    }

    public boolean isEmpty(final Locale locale) {
        containsLocaleMap(locale);
        return bundles.get(locale).isEmpty();
    }

    public Set<String> keySet(final Locale locale) {
        containsLocaleMap(locale);
        return bundles.get(locale).keySet();
    }

    public ResourceBundle put(final Locale locale, final String key, final ResourceBundle value) {
        containsLocaleMap(locale);
        return bundles.get(locale).put(key, value);
    }

    public void putAll(final Locale locale, final Map<? extends String, ? extends ResourceBundle> m) {
        containsLocaleMap(locale);
        bundles.get(locale).putAll(m);
    }

    public ResourceBundle remove(final Locale locale, final Object key) {
        containsLocaleMap(locale);
        return bundles.get(locale).remove(key);
    }

    public int size(final Locale locale) {
        containsLocaleMap(locale);
        return bundles.get(locale).size();
    }

    public Collection<ResourceBundle> values(final Locale locale) {
        containsLocaleMap(locale);
        return bundles.get(locale).values();
    }

    private void containsLocaleMap(Locale locale) {
        if (!bundles.containsKey(locale)) {
            bundles.put(locale, new ConcurrentHashMap<String, ResourceBundle>());
        }
    }
}
