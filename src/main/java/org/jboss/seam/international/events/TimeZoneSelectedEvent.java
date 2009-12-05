package org.jboss.seam.international.events;

/**
 * This event is raised when a time zone is selected
 * 
 * @author Shane Bryzak
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
