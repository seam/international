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
package org.jboss.seam.international.jdktimezone;

import java.io.Serializable;
import java.util.TimeZone;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.solder.logging.Logger;
import org.jboss.seam.international.timezone.DefaultTimeZone;

/**
 * Default TimeZone of the application. If configuration of the default TimeZone is found that will be used, otherwise the JVM
 * default TimeZone.
 *
 * @author Ken Finnigan
 */

@ApplicationScoped
public class DefaultTimeZoneProducer implements Serializable {
    private static final long serialVersionUID = 3277798729003795202L;

    @Inject
    @DefaultTimeZone
    private Instance<String> defaultTimeZoneId;

    private final Logger log = Logger.getLogger(DefaultTimeZoneProducer.class);

    @Produces
    @Named
    private TimeZone defaultTimeZone = null;

    @PostConstruct
    public void init() {
        if (!defaultTimeZoneId.isUnsatisfied()) {
            try {
                String id = defaultTimeZoneId.get();
                TimeZone dtz = TimeZone.getTimeZone(id);
                if (!dtz.getID().equals(id)) {
                    throw new IllegalArgumentException();
                }
                defaultTimeZone = constructTimeZone(dtz);
            } catch (IllegalArgumentException e) {
                log.warn("DefaultTimeZoneProducer: Default TimeZone Id of " + defaultTimeZoneId.get() + " was not found");
            }
        }
        if (null == defaultTimeZone) {
            TimeZone dtz = TimeZone.getDefault();
            defaultTimeZone = constructTimeZone(dtz);
        }
    }

    private ForwardingTimeZone constructTimeZone(final TimeZone dtz) {
        return new ForwardingTimeZone(dtz.getID()) {
            private static final long serialVersionUID = 8409832600089507805L;

            @Override
            protected TimeZone delegate() {
                return dtz;
            }

            @Override
            public void setRawOffset(int offsetMillis) {
                throw new UnsupportedOperationException();
            }
        };
    }
}
