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
package org.jboss.seam.international.datetimezone;

import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.timezone.DefaultTimeZone;
import org.jboss.solder.core.Requires;
import org.jboss.solder.logging.Logger;
import org.joda.time.DateTimeZone;

/**
 * Default DateTimeZone of the application. If configuration of the default DateTimeZone is found that will be used, otherwise
 * the JVM default TimeZone.
 *
 * @author Ken Finnigan
 */

@Requires({"org.joda.time.DateTimeZone"})
@ApplicationScoped
public class DefaultDateTimeZoneProducer implements Serializable {
    private static final long serialVersionUID = 6181892144731122500L;

    @Inject
    @DefaultTimeZone
    private Instance<String> defaultTimeZoneId;

    private final Logger log = Logger.getLogger(DefaultDateTimeZoneProducer.class);

    @Produces
    @Named
    private DateTimeZone defaultDateTimeZone = null;

    @PostConstruct
    public void init() {
        if (!defaultTimeZoneId.isUnsatisfied()) {
            try {
                String id = defaultTimeZoneId.get();
                DateTimeZone dtz = DateTimeZone.forID(id);
                defaultDateTimeZone = constructTimeZone(dtz);
            } catch (IllegalArgumentException e) {
                log.warn("DefaultDateTimeZoneProducer: Default TimeZone Id of " + defaultTimeZoneId + " was not found");
            }
        }
        if (null == defaultDateTimeZone) {
            DateTimeZone dtz = DateTimeZone.getDefault();
            defaultDateTimeZone = constructTimeZone(dtz);
        }
    }

    private ForwardingDateTimeZone constructTimeZone(final DateTimeZone dtz) {
        return new ForwardingDateTimeZone(dtz.getID()) {
            @Override
            protected DateTimeZone delegate() {
                return dtz;
            }
        };
    }
}
