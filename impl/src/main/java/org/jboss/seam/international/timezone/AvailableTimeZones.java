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
package org.jboss.seam.international.timezone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import org.joda.time.DateTimeZone;

/**
 * <p>
 * Seam component that provides a list of time zones, limited to time zones with
 * IDs in the form Continent/Place, excluding deprecated three-letter time zone
 * IDs. The time zones returned have a fixed offset from UTC, which takes
 * daylight savings time into account. For example, Europe/Amsterdam is UTC+1;
 * in winter this is GMT+1 and in summer GMT+2.
 * </p>
 * 
 * <p>
 * The time zone objects returned are wrapped in an implementation of TimeZone
 * that provides a more friendly interface for accessing the time zone
 * information. In particular, this type provides a more bean-friend property
 * for the time zone id (id than ID) and provides a convenience property named
 * label that formats the time zone for display in the UI. This wrapper can be
 * disabled by setting the component property wrap to false.
 * </p>
 * 
 * @author Peter Hilton, Lunatech Research
 * @author Dan Allen
 */
@ApplicationScoped
public class AvailableTimeZones
{
   private static final String TIMEZONE_ID_PREFIXES = "^(Africa|America|Asia|Atlantic|Australia|Europe|Indian|Pacific)/.*";

   @Produces
   private List<ForwardingTimeZone> timeZones = null;

   @SuppressWarnings("unchecked")
   @PostConstruct
   public void init()
   {
      timeZones = new ArrayList<ForwardingTimeZone>();
      final Set timeZoneIds = DateTimeZone.getAvailableIDs();
      for (Object object : timeZoneIds)
      {
         String id = (String) object;
         if (id.matches(TIMEZONE_ID_PREFIXES))
         {
            final DateTimeZone dtz = DateTimeZone.forID(id);
            timeZones.add(new ForwardingTimeZone(id)
            {
               @Override
               protected DateTimeZone delegate()
               {
                  return dtz;
               }
            });
         }
      }
      Collections.sort(timeZones, new Comparator<ForwardingTimeZone>()
      {
         public int compare(final ForwardingTimeZone a, final ForwardingTimeZone b)
         {
            return a.getID().compareTo(b.getID());
         }
      });
      timeZones = Collections.unmodifiableList(timeZones);
   }
}
