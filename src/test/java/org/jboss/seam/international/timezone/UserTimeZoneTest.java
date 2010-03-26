package org.jboss.seam.international.timezone;

import java.util.TimeZone;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.timezone.events.TimeZoneSelectedEvent;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.Archives;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.asset.ByteArrayAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UserTimeZoneTest
{
   @Deployment
   public static JavaArchive createTestArchive()
   {
      return Archives.create("usertimezonetest.jar", JavaArchive.class).addClasses(UserTimeZoneProducer.class, UserTimeZone.class, TimeZoneSelectedEvent.class, DefaultTimeZone.class, DefaultTimeZoneProducer.class).addManifestResource(new ByteArrayAsset(new byte[0]), ArchivePaths.create("beans.xml"));
   }

   @Inject
   @UserTimeZone
   TimeZone timeZone;
   @Inject
   Event<TimeZoneSelectedEvent> timeZoneEvent;
   @Inject
   @UserTimeZone
   Instance<TimeZone> timeZoneSource;

   @Test
   public void testUserTimeZoneProducerDirect()
   {
      Assert.assertNotNull(timeZone);
   }

   @Test
   public void testUserTimeZoneEvent()
   {
      TimeZone tijuana = TimeZone.getTimeZone("America/Tijuana");
      Assert.assertNotNull(timeZone);
      Assert.assertFalse(timeZone.equals(tijuana));
      timeZoneEvent.fire(new TimeZoneSelectedEvent("America/Tijuana"));
      TimeZone tz = timeZoneSource.get();
      Assert.assertNotNull(tz);
      Assert.assertTrue(tz.equals(tijuana));
   }
}
