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

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.jboss.solder.core.Requires;
import org.joda.time.DateTimeZone;

/**
 * <p>
 * Seam component that provides a list of time zones, limited to time zones with IDs in the form Continent/Place, excluding
 * deprecated three-letter time zone IDs. The time zones returned have a fixed offset from UTC, which takes daylight savings
 * time into account. For example, Europe/Amsterdam is UTC+1; in winter this is GMT+1 and in summer GMT+2.
 * </p>
 * <p/>
 * <p>
 * The time zone objects returned are wrapped in an implementation of TimeZone that provides a more friendly interface for
 * accessing the time zone information. In particular, this type provides a more bean-friend property for the time zone id (id
 * than ID) and provides a convenience property named label that formats the time zone for display in the UI. This wrapper can
 * be disabled by setting the component property wrap to false.
 * </p>
 *
 * @author Peter Hilton, Lunatech Research
 * @author Dan Allen
 */
@Requires({"org.joda.time.DateTimeZone"})
@ApplicationScoped
public class AvailableDateTimeZones {
    private static final String TIMEZONE_ID_PREFIXES = "^(Africa|America|Asia|Atlantic|Australia|Europe|Indian|Pacific)/.*";

    @Produces
    private List<ForwardingDateTimeZone> dateTimeZones = null;

    @SuppressWarnings("unchecked")
    @PostConstruct
    public void init() {
        dateTimeZones = new ArrayList<ForwardingDateTimeZone>();
        final Set timeZoneIds = DateTimeZone.getAvailableIDs();
        for (Object object : timeZoneIds) {
            String id = (String) object;
            if (id.matches(TIMEZONE_ID_PREFIXES)) {
                final DateTimeZone dtz = DateTimeZone.forID(id);
                dateTimeZones.add(new ForwardingDateTimeZone(id) {
                    @Override
                    protected DateTimeZone delegate() {
                        return dtz;
                    }
                });
            }
        }
        Collections.sort(dateTimeZones, new Comparator<ForwardingDateTimeZone>() {
            public int compare(final ForwardingDateTimeZone a, final ForwardingDateTimeZone b) {
                return a.getID().compareTo(b.getID());
            }
        });
        dateTimeZones = Collections.unmodifiableList(dateTimeZones);
    }
}
