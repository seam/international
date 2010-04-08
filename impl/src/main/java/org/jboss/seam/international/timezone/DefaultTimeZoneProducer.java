package org.jboss.seam.international.timezone;

import java.io.Serializable;
import java.util.TimeZone;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Default TimeZone of the application.  If configuration of the default
 * TimeZone is found that will be used, otherwise the JVM default TimeZone
 * otherwise.
 *
 * @author Ken Finnigan
 */

@ApplicationScoped
public class DefaultTimeZoneProducer implements Serializable
{
   private static final long serialVersionUID = 6181892144731122500L;

   @Produces @DefaultTimeZone @Named
   private TimeZone defaultTimeZone;

   @Inject
   public void init()
   {
      defaultTimeZone = TimeZone.getDefault();
   }
}
