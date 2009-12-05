package org.jboss.seam.international;

import java.util.Date;
import java.util.TimeZone;

/**
 * A wrapper around a TimeZone that provides a more convenience interface to
 * access the time zone information in the UI, in particular in the options of
 * a select menu.
 *
 * @author Dan Allen
 */
public class TimeZoneWrapper extends TimeZone
{
   private static final int MILLISECONDS_PER_HOUR = 1000 * 60 * 60;

   private TimeZone timeZone;

   public TimeZoneWrapper(TimeZone tz) {
      timeZone = tz;
      setID(tz.getID());
   }

   @Override
   public void setID(String id) {
      super.setID(id);
      timeZone = TimeZone.getTimeZone(id);
   }

   public String getId() {
      return timeZone.getID();
   }

   public String getLabel() {
      StringBuilder label = new StringBuilder(50);
      label.append(getId().replace("_", " "));
      label.append(" (UTC");
      label.append(timeZone.getRawOffset() > 0 ? "+" : "-");
      if (Math.abs(timeZone.getRawOffset()) < MILLISECONDS_PER_HOUR * 10) {
         label.append("0");
      }
      label.append(Math.abs(timeZone.getRawOffset())/MILLISECONDS_PER_HOUR);
      label.append(":00)");
      return label.toString();
   }

   public TimeZone getTimeZone() {
      return timeZone;
   }

   @Override
   public int getOffset(int era, int year, int month, int day, int dayOfWeek, int millis) {
      return timeZone.getOffset(era, year, month, day, dayOfWeek, millis);
   }

   @Override
   public void setRawOffset(int offset) {
      timeZone.setRawOffset(offset);
   }

   @Override
   public int getRawOffset() {
      return timeZone.getRawOffset();
   }

   @Override
   public boolean useDaylightTime() {
      return timeZone.useDaylightTime();
   }

   @Override
   public boolean inDaylightTime(Date date) {
      return timeZone.inDaylightTime(date);
   }

   @Override
   public Object clone() {
      return timeZone.clone();
   }

   @Override
   public boolean equals(Object obj) {
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      final TimeZoneWrapper other = (TimeZoneWrapper) obj;
      if (timeZone != other.timeZone && (timeZone == null || !timeZone.equals(other.timeZone))) {
         return false;
      }
      return true;
   }

   @Override
   public int hashCode() {
      int hash = 7;
      hash = 79 * hash + (timeZone != null ? timeZone.hashCode() : 0);
      return hash;
   }
}
