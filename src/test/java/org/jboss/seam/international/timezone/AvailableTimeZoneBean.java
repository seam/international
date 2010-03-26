package org.jboss.seam.international.timezone;

import java.util.List;
import java.util.TimeZone;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
public class AvailableTimeZoneBean
{
   @Inject
   @TimeZones
   private List<TimeZone> timeZones;

   public AvailableTimeZoneBean()
   {      
   }

   public List<TimeZone> getAvailTimeZones()
   {
      return timeZones;
   }
}
