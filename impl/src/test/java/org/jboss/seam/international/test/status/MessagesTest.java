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
package org.jboss.seam.international.test.status;

import static org.junit.Assert.assertEquals;

import javax.inject.Inject;

import org.jboss.arquillian.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.status.Bundles;
import org.jboss.seam.international.status.Level;
import org.jboss.seam.international.status.MessageFactory;
import org.jboss.seam.international.status.MessageImpl;
import org.jboss.seam.international.status.MessagesImpl;
import org.jboss.seam.international.status.MutableMessage;
import org.jboss.seam.international.status.builder.BundleKey;
import org.jboss.seam.international.status.builder.BundleTemplateMessageImpl;
import org.jboss.seam.international.status.builder.TemplateMessageImpl;
import org.jboss.shrinkwrap.api.ArchivePaths;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.impl.base.asset.ByteArrayAsset;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * 
 */
@RunWith(Arquillian.class)
public class MessagesTest
{
   private static final String BUNDLE_PATH = "org.jboss.seam.international.test.status.TestBundle";

   @Deployment
   public static JavaArchive createTestArchive()
   {
      return ShrinkWrap.create("test.jar", JavaArchive.class).addClasses(MessagesImpl.class, MessageFactory.class, BundleTemplateMessageImpl.class, TemplateMessageImpl.class, Bundles.class).addManifestResource(new ByteArrayAsset(new byte[0]), ArchivePaths.create("beans.xml"));
   }

   @Inject
   MessagesImpl messages;

   @Before
   public void before()
   {
      messages.clear();
   }

   @Test
   public void testMessageBuildersAreAddedWhenUsingFactoryMethods()
   {
      messages.info("This is a message");
      assertEquals(1, messages.getAll().size());
      assertEquals(Level.INFO, messages.getAll().iterator().next().getLevel());
   }

   @Test
   public void testMessageBuildersAreAddedWhenUsingFactoryMethodsBundle()
   {
      messages.error(new BundleKey(BUNDLE_PATH, "key1"));
      assertEquals(1, messages.getAll().size());
      assertEquals(Level.ERROR, messages.getAll().iterator().next().getLevel());
   }

   @Test
   public void testDuplicateMessageBuildersAreIgnored()
   {
      messages.warn("This is a message");
      messages.warn("This is a message");
      messages.warn("This is a message");
      assertEquals(1, messages.getAll().size());

      assertEquals(Level.WARN, messages.getAll().iterator().next().getLevel());
   }

   @Test
   public void testDuplicateMessageBuildersResultMessagesAreIgnored()
   {
      messages.fatal("This is a message");
      messages.fatal(new BundleKey(BUNDLE_PATH, "key2"));
      assertEquals(1, messages.getAll().size());

      assertEquals(Level.FATAL, messages.getAll().iterator().next().getLevel());
   }

   @Test
   public void testDuplicateMessagesAreIgnored() throws Exception
   {
      MutableMessage message = new MessageImpl();
      String text = "This is a message!";
      message.setText(text);
      messages.add(message);

      message = new MessageImpl();
      message.setText(text);
      messages.add(message);

      assertEquals(1, messages.getAll().size());
   }
}
