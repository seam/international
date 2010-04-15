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

import java.util.Date;
import java.util.TimeZone;

/**
 * A wrapper around a TimeZone that provides a more convenience interface to
 * access the time zone information in the UI, in particular in the options of a
 * select menu.
 * 
 * @author Dan Allen
 */
public class TimeZoneWrapper extends TimeZone
{
   private static final long serialVersionUID = 8504687846433050650L;

   private static final int MILLISECONDS_PER_HOUR = 1000 * 60 * 60;

   private TimeZone timeZone;

   public TimeZoneWrapper(TimeZone tz)
   {
      timeZone = tz;
      setID(tz.getID());
   }

   @Override
   public void setID(String id)
   {
      super.setID(id);
      timeZone = TimeZone.getTimeZone(id);
   }

   public String getId()
   {
      return timeZone.getID();
   }

   public String getLabel()
   {
      StringBuilder label = new StringBuilder(50);
      label.append(getId().replace("_", " "));
      label.append(" (UTC");
      label.append(timeZone.getRawOffset() > 0 ? "+" : "-");
      if (Math.abs(timeZone.getRawOffset()) < MILLISECONDS_PER_HOUR * 10)
      {
         label.append("0");
      }
      label.append(Math.abs(timeZone.getRawOffset()) / MILLISECONDS_PER_HOUR);
      label.append(":00)");
      return label.toString();
   }

   public TimeZone getTimeZone()
   {
      return timeZone;
   }

   @Override
   public int getOffset(int era, int year, int month, int day, int dayOfWeek, int millis)
   {
      return timeZone.getOffset(era, year, month, day, dayOfWeek, millis);
   }

   @Override
   public void setRawOffset(int offset)
   {
      timeZone.setRawOffset(offset);
   }

   @Override
   public int getRawOffset()
   {
      return timeZone.getRawOffset();
   }

   @Override
   public boolean useDaylightTime()
   {
      return timeZone.useDaylightTime();
   }

   @Override
   public boolean inDaylightTime(Date date)
   {
      return timeZone.inDaylightTime(date);
   }

   @Override
   public Object clone()
   {
      return timeZone.clone();
   }

   @Override
   public boolean equals(Object obj)
   {
      if (obj == null)
      {
         return false;
      }
      if (getClass() != obj.getClass())
      {
         return false;
      }
      final TimeZoneWrapper other = (TimeZoneWrapper) obj;
      if (timeZone != other.timeZone && (timeZone == null || !timeZone.equals(other.timeZone)))
      {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode()
   {
      int hash = 7;
      hash = 79 * hash + (timeZone != null ? timeZone.hashCode() : 0);
      return hash;
   }
}
