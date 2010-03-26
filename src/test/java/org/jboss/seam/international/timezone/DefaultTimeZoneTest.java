package org.jboss.seam.international.timezone;

import java.util.TimeZone;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.Archives;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.asset.ByteArrayAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class DefaultTimeZoneTest
{
   @Deployment
   public static JavaArchive createTestArchive()
   {
      return Archives.create("defaulttimezonetest.jar", JavaArchive.class).addClasses(DefaultTimeZoneProducer.class, DefaultTimeZone.class).addManifestResource(new ByteArrayAsset(new byte[0]), ArchivePaths.create("beans.xml"));
   }

   @Inject
   @DefaultTimeZone
   TimeZone timeZone;

   @Test
   public void testDefaultTimeZoneProducerDirect()
   {
      Assert.assertNotNull(timeZone);
   }
}
