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
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;

/**
 * Maintains a global map of {@link ResourceBundle} objects.
 * 
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@Named
@ApplicationScoped
public class Bundles implements Map<String, ResourceBundle>, Serializable {
    private static final long serialVersionUID = 1207758648760266247L;

    private final Map<String, ResourceBundle> bundles = new ConcurrentHashMap<String, ResourceBundle>();

    public void clear() {
        bundles.clear();
    }

    public boolean containsKey(final Object key) {
        return bundles.containsKey(key);
    }

    public boolean containsValue(final Object value) {
        return bundles.containsValue(value);
    }

    public Set<java.util.Map.Entry<String, ResourceBundle>> entrySet() {
        return bundles.entrySet();
    }

    public ResourceBundle get(final Object key) {
        if (!containsKey(key)) {
            ResourceBundle bundle = ResourceBundle.getBundle(key.toString());
            bundles.put(key.toString(), bundle);
        }
        return bundles.get(key);
    }

    public boolean isEmpty() {
        return bundles.isEmpty();
    }

    public Set<String> keySet() {
        return keySet();
    }

    public ResourceBundle put(final String key, final ResourceBundle value) {
        return bundles.put(key, value);
    }

    public void putAll(final Map<? extends String, ? extends ResourceBundle> m) {
        bundles.putAll(m);
    }

    public ResourceBundle remove(final Object key) {
        return bundles.remove(key);
    }

    public int size() {
        return bundles.size();
    }

    public Collection<ResourceBundle> values() {
        return bundles.values();
    }
}