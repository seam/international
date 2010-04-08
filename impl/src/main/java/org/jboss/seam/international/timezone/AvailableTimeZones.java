package org.jboss.seam.international.timezone;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.TimeZone;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

/**
 * <p>Seam component that provides a list of time zones, limited to time zones
 * with IDs in the form Continent/Place, excluding deprecated three-letter time
 * zone IDs. The time zones returned have a fixed offset from UTC, which takes
 * daylight savings time into account. For example, Europe/Amsterdam is UTC+1;
 * in winter this is GMT+1 and in summer GMT+2.</p>
 *
 * <p>The time zone objects returned are wrapped in an implementation of
 * TimeZone that provides a more friendly interface for accessing the time zone
 * information. In particular, this type provides a more bean-friend property
 * for the time zone id (id than ID) and provides a convenience property named
 * label that formats the time zone for display in the UI. This wrapper can be
 * disabled by setting the component property wrap to false.</p>
 *
 * @author Peter Hilton, Lunatech Research
 * @author Dan Allen
 */
@ApplicationScoped
public class AvailableTimeZones
{
   private static final String TIMEZONE_ID_PREFIXES =
      "^(Africa|America|Asia|Atlantic|Australia|Europe|Indian|Pacific)/.*";

   private boolean wrap = true;

   @Produces
   @TimeZones
   private List<TimeZone> timeZones = null;
   
   @Inject
   public void init() {
      timeZones = new ArrayList<TimeZone>();
      final String[] timeZoneIds = TimeZone.getAvailableIDs();
      for (final String id : timeZoneIds) {
         if (id.matches(TIMEZONE_ID_PREFIXES)) {
            timeZones.add(wrap ? new TimeZoneWrapper(TimeZone.getTimeZone(id)) : TimeZone.getTimeZone(id));
         }
      }
      Collections.sort(timeZones, new Comparator<TimeZone>() {
         public int compare(final TimeZone a, final TimeZone b) {
            return a.getID().compareTo(b.getID());
         }
      });
   }

   public boolean isWrap() {
      return wrap;
   }

   public void setWrap(boolean wrap) {
      this.wrap = wrap;
   }
}
