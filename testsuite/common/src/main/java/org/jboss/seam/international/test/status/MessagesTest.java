/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.international.test.status;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.inject.Inject;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.seam.international.status.Level;
import org.jboss.seam.international.status.MessageImpl;
import org.jboss.seam.international.status.MessagesImpl;
import org.jboss.seam.international.status.MutableMessage;
import org.jboss.seam.international.status.builder.BundleKey;
import org.jboss.seam.international.test.util.Deployments;
import org.jboss.seam.international.test.util.Libraries;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 */
@RunWith(Arquillian.class)
public class MessagesTest {
    private static final String BUNDLE_PATH = "org.jboss.seam.international.test.status.TestBundle";

    @Deployment(name = "Messages")
    public static WebArchive createTestArchive() {
        WebArchive war = Deployments.addEmptyBeansXML(Deployments.createWebArchive())
                .addAsLibraries(Libraries.seamIntlLibraryWithTransitives())
                .addAsResource(BUNDLE_PATH.replace('.', '/') + ".properties");
        return war;
    }

    @Inject
    MessagesImpl messages;

    @Before
    public void before() {
        messages.clear();
    }

    @Test
    public void testMessageBuildersAreAddedWhenUsingFactoryMethods() {
        messages.info("This is a message");
        assertEquals(1, messages.getAll().size());
        assertEquals(Level.INFO, messages.getAll().iterator().next().getLevel());
    }

    @Test
    public void testMessageBuildersAreAddedWhenUsingFactoryMethodsAll() {
        messages.info("This is a %s", "message");
        messages.error("This is a message");
        messages.error("This is a %s", "message");
        messages.fatal("This is a message");
        messages.fatal("This is a %s", "message");
        messages.warn("This is a message");
        messages.warn("This is a %s", "message");
        assertEquals(7, messages.getAll().size());
    }

    @Test
    public void testMessageBuildersAreAddedWhenUsingFactoryMethodsBundle() {
        messages.warn(new BundleKey(BUNDLE_PATH, "key1"));
        assertEquals(1, messages.getAll().size());
        assertEquals(Level.WARN, messages.getAll().iterator().next().getLevel());
    }

    @Test
    public void testMessageBuildersAreAddedWhenUsingFactoryMethodsBundleAll() {
        messages.error(new BundleKey(BUNDLE_PATH, "key1"));
        messages.error(new BundleKey(BUNDLE_PATH, "key1"), "something");
        messages.info(new BundleKey(BUNDLE_PATH, "key1"));
        messages.info(new BundleKey(BUNDLE_PATH, "key1"), "something");
        messages.warn(new BundleKey(BUNDLE_PATH, "key1"));
        messages.warn(new BundleKey(BUNDLE_PATH, "key1"), "something");
        messages.fatal(new BundleKey(BUNDLE_PATH, "key1"), "something");
        assertFalse(messages.isEmpty());
        assertEquals(7, messages.getAll().size());
    }

    @Test
    public void testDuplicateMessageBuildersAreIgnored() {
        messages.warn("This is a message");
        messages.warn("This is a message");
        messages.warn("This is a message");
        assertEquals(1, messages.getAll().size());

        assertEquals(Level.WARN, messages.getAll().iterator().next().getLevel());
    }

    @Test
    public void testDuplicateMessageBuildersResultMessagesAreIgnored() {
        messages.fatal("This is a message");
        messages.fatal(new BundleKey(BUNDLE_PATH, "key2"));
        assertEquals(1, messages.getAll().size());

        assertEquals(Level.FATAL, messages.getAll().iterator().next().getLevel());
    }

    @Test
    public void testDuplicateMessagesAreIgnored() throws Exception {
        MutableMessage message = new MessageImpl();
        String text = "This is a message!";
        message.setText(text);
        messages.add(message);

        MutableMessage message2 = new MessageImpl();
        message2.setText(text);
        messages.add(message2);

        assertEquals(1, messages.getAll().size());
        assertTrue(message.equals(message2));
    }

    @Test
    public void testEmptyMessages() {
        assertTrue(messages.isEmpty());
        assertEquals(0, messages.getAll().size());
    }

    @Test
    public void testMessageEquals() {
        MutableMessage message = new MessageImpl();
        String text = "This is a message!";
        message.setText(text);

        MutableMessage message2 = new MessageImpl();
        message2.setText(text);

        assertTrue(message.equals(message2));
        MutableMessage message3 = message;
        assertTrue(message.equals(message3));
        assertFalse(message.equals(null));
        assertFalse(message.equals("message"));
        message.setLevel(Level.FATAL);
        message2.setLevel(Level.WARN);
        assertFalse(message.equals(message2));
        message.setLevel(null);
        assertFalse(message.equals(message2));
        message.setLevel(Level.WARN);
        message2.setTargets("targets");
        assertFalse(message.equals(message2));
        message.setTargets("otherTarget");
        assertFalse(message.equals(message2));
        assertTrue(message.getTargets().equals("otherTarget"));

        message = new MessageImpl();
        message2 = new MessageImpl();
        message2.setText("summary");
        assertFalse(message.equals(message2));
        message.setText("different summary");
        assertFalse(message.equals(message2));
    }
}
