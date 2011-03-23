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

import javax.inject.Inject;

import org.jboss.seam.solder.core.Client;

/**
 * Access to map of {@link ResourceBundle} objects for user locale.
 * 
 * @author <a href="http://community.jboss.org/people/ssachtleben">Sebastian Sachtleben</a>
 */
public class Bundles implements Map<String, ResourceBundle>, Serializable {
    private static final long serialVersionUID = -1608918108928277728L;

    @Inject
    private ApplicationBundles bundles;

    @Inject
    @Client
    private Locale locale;

    public int size() {
        return bundles.size(locale);
    }

    public boolean isEmpty() {
        return bundles.isEmpty(locale);
    }

    public boolean containsKey(final Object key) {
        return bundles.containsKey(locale, key);
    }

    public boolean containsValue(final Object value) {
        return bundles.containsValue(locale, value);
    }

    public ResourceBundle get(final Object key) {
        return bundles.get(locale, key);
    }

    public ResourceBundle put(final String key, final ResourceBundle value) {
        return bundles.put(locale, key, value);
    }

    public ResourceBundle remove(final Object key) {
        return bundles.remove(locale, key);
    }

    public void putAll(final Map<? extends String, ? extends ResourceBundle> m) {
        bundles.putAll(locale, m);
    }

    public void clear() {
        bundles.clear(locale);
    }

    public Set<String> keySet() {
        return bundles.keySet(locale);
    }

    public Collection<ResourceBundle> values() {
        return bundles.values(locale);
    }

    public Set<java.util.Map.Entry<String, ResourceBundle>> entrySet() {
        return bundles.entrySet(locale);
    }
}
