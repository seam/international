package org.jboss.seam.international.timezone;

import java.util.List;
import java.util.TimeZone;

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
public class AvailableTimeZonesTest
{
   @Deployment
   public static JavaArchive createTestArchive()
   {
      return Archives.create("availabletimezonetest.jar", JavaArchive.class).addClasses(AvailableTimeZones.class, AvailableTimeZoneBean.class, TimeZoneWrapper.class, TimeZoneSelectedEvent.class).addManifestResource(new ByteArrayAsset(new byte[0]), ArchivePaths.create("beans.xml"));
   }

   @Inject
   Instance<AvailableTimeZoneBean> availBean;
   @Inject
   @TimeZones
   List<TimeZone> timeZones;

   @Test
   public void testAvailableTimeZonesProducerViaBean()
   {
      Assert.assertNotNull(availBean);
      List<TimeZone> list = availBean.get().getAvailTimeZones();
      Assert.assertNotNull(list);
      Assert.assertTrue(!list.isEmpty());
      Assert.assertTrue(list.size() > 0);
   }

   @Test
   public void testAvailableTimeZonesProducerDirect()
   {
      Assert.assertNotNull(timeZones);
      Assert.assertTrue(!timeZones.isEmpty());
      Assert.assertTrue(timeZones.size() > 0);
   }
}
