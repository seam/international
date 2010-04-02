package org.jboss.seam.international.timezone.events;

/**
 * Event raised when TimeZone selected.
 *
 * @author Ken Finnigan
 */
public class TimeZoneSelectedEvent
{
   private String timeZoneId;
   
   public TimeZoneSelectedEvent(String timeZoneId)
   {
      this.timeZoneId = timeZoneId;
   }
   
   public String getTimeZoneId()
   {
      return timeZoneId;
   }
}
