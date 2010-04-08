package org.jboss.seam.international.timezone;

import java.io.Serializable;
import java.util.TimeZone;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.timezone.events.TimeZoneSelectedEvent;

/**
 * TimeZone for a User Session.  Defaults to the TimeZone within
 * DefaultTimeZone and is altered when it receives the
 * TimeZoneSelectedEvent.
 *
 * @author Ken Finnigan
 */

@SessionScoped
public class UserTimeZoneProducer implements Serializable
{
   private static final long serialVersionUID = -9008203923830420841L;

   @Produces @UserTimeZone @Named
   private TimeZone userTimeZone;

   @Inject
   public void init(@DefaultTimeZone TimeZone defaultTimeZone)
   {
      this.userTimeZone = defaultTimeZone;
   }

   public void afterTimeZoneUpdate(@Observes TimeZoneSelectedEvent event)
   {
      this.userTimeZone = TimeZone.getTimeZone(event.getTimeZoneId());
   }
}
