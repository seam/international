/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.international.test.locale;

import java.util.Locale;

import javax.enterprise.event.Event;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.Changed;
import org.jboss.seam.international.locale.DefaultLocaleProducer;
import org.jboss.seam.international.locale.UserLocale;
import org.jboss.seam.international.locale.UserLocaleProducer;
import org.jboss.seam.international.test.MockLogger;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.asset.ByteArrayAsset;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class UserLocaleTest
{
   @Deployment
   public static JavaArchive createTestArchive()
   {
      return ShrinkWrap.create("userlocaletest.jar", JavaArchive.class).addClasses(MockLogger.class, UserLocaleProducer.class, UserLocale.class, DefaultLocaleProducer.class).addManifestResource(new ByteArrayAsset(new byte[0]), ArchivePaths.create("beans.xml"));
   }

   @Inject
   @UserLocale
   Locale locale;

   @Inject
   @Changed
   Event<Locale> localeEvent;

   @Inject
   @UserLocale
   Instance<Locale> localeSource;

   @Test
   public void testUserLocaleProducerDirect()
   {
      Assert.assertNotNull(locale);
   }

   @Test
   public void testUserLocaleEvent()
   {
      Locale canada = Locale.CANADA;
      Assert.assertNotNull(locale);
      Assert.assertFalse(locale.equals(canada));
      localeEvent.fire(canada);
      Locale lc = localeSource.get();
      Assert.assertNotNull(lc);
      Assert.assertTrue(lc.equals(canada));
   }
}
