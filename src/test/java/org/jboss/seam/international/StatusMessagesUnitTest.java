package org.jboss.seam.international;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertSame;

import java.util.List;

import javax.validation.ConstraintDescriptor;
import javax.validation.ConstraintViolation;

import org.testng.annotations.Test;

/**
 * A set of tests which exercise the StatusMessages as a unit (no external dependencies).
 * These tests focus primarily on the creation and storage of the StatusMessage.
 *
 * @author Dan Allen
 * @see StatusMessages
 */
@Test(groups = "unit")
public class StatusMessagesUnitTest
{
   /**
    * Check that the add() method appends a global message, that the
    * default severity is INFO, and that interpolation is deferred.
    */
   @Test
   public void testAddGlobalMessageFromTemplateWithDefaultSeverity()
   {
      StatusMessages statusMessages = getStatusMessagesInstance();
      statusMessages.add("Simple message");
      assertEquals(statusMessages.getGlobalMessages().size(), 1);
      assertEquals(statusMessages.getKeyedMessages().size(), 0);
      StatusMessage message = statusMessages.getGlobalMessages().get(0);
      assertSame(message.getSeverity(), StatusMessage.Severity.INFO);
      assertFalse(message.isEmpty());
      assertNull(message.getSummary());
      statusMessages.onBeforeRender();
      assertEquals(message.getSummary(), "Simple message");
      assertNull(message.getDetail());
   }

   /**
    * Test that separate summary and detail templates can be specified.
    */
   @Test
   public void testAddMessageWithSummaryAndDetail()
   {
      StatusMessages statusMessages = getStatusMessagesInstance();
      statusMessages.add(StatusMessage.Severity.WARN, null, null, "Summary template", "Detail template");
      assertEquals(statusMessages.getGlobalMessages().size(), 1);
      StatusMessage message = statusMessages.getGlobalMessages().get(0);
      assertSame(message.getSeverity(), StatusMessage.Severity.WARN);
      statusMessages.onBeforeRender();
      assertEquals(message.getSummary(), "Summary template");
      assertEquals(message.getDetail(), "Detail template");
   }

   /**
    * Test that the severity is processed correctly when adding a global message.
    */
   @Test
   public void testAddGlobalWarningMessageFromTemplate()
   {
      StatusMessages statusMessages = getStatusMessagesInstance();
      statusMessages.add(StatusMessage.Severity.WARN, "Achtung!");
      assertEquals(statusMessages.getGlobalMessages().size(), 1);
      assertSame(statusMessages.getGlobalMessages().get(0).getSeverity(), StatusMessage.Severity.WARN);
   }

   /**
    * Test that the addToControl() associates a message with a client id, that the
    * default severity is INFO, and that interpolation is deferred.
    */
   @Test
   public void testAddMessageToControlFromTemplateWithDefaultSeverity()
   {
      StatusMessages statusMessages = getStatusMessagesInstance();
      statusMessages.addToControl("username", "Available!");
      List<StatusMessage> messages = statusMessages.getKeyedMessages("username");
      assertNotNull(messages);
      assertEquals(messages.size(), 1);
      StatusMessage message = messages.get(0);
      assertSame(message.getSeverity(), StatusMessage.Severity.WARN);
      assertNull(message.getSummary());
      statusMessages.onBeforeRender();
      assertEquals(message.getSummary(), "Available!");
      assertNull(message.getDetail());
      statusMessages.addToControl("username", "Nice choice");
      assertEquals(statusMessages.getKeyedMessages("username").size(), 2);
   }

   /**
    * Test that add() can create a global message with severity WARN from the contraint violation message.
    */
   @Test
   public void testAddGlobalMessageFromConstraintViolation()
   {
      StatusMessages statusMessages = getStatusMessagesInstance();
      ConstraintViolation<Object> violation = new ConstraintViolationStub("Invalid number", "creditCardNumber");
      statusMessages.add(violation);
      assertEquals(statusMessages.getGlobalMessages().size(), 1);
      assertEquals(statusMessages.getKeyedMessages().size(), 0);
      StatusMessage message = statusMessages.getGlobalMessages().get(0);
      assertSame(message.getSeverity(), StatusMessage.Severity.WARN);
      statusMessages.onBeforeRender();
      assertEquals(message.getSummary(), "Invalid number");
   }

   /**
    * Test that addToControl() can create a message associated with the "client id" that is equivalent to the
    * last path segment of the property path with severity WARN from the contraint violation message.
    */
   @Test
   public void testAddMessageToControlsDynamicallyFromConstraintViolation()
   {
      StatusMessages statusMessages = getStatusMessagesInstance();
      ConstraintViolation<Object> firstNameViolation = new ConstraintViolationStub("Too short", "firstName");
      ConstraintViolation<Object> lastNameViolation = new ConstraintViolationStub("Too long", "lastName");
      ConstraintViolation<Object> usernameViolation = new ConstraintViolationStub("Already taken", "account.username");
      statusMessages.addToControl(firstNameViolation);
      statusMessages.addToControls(new ConstraintViolation[] { lastNameViolation, usernameViolation });
      assertEquals(statusMessages.getGlobalMessages().size(), 0);
      assertEquals(statusMessages.getKeyedMessages().size(), 3);
      List<StatusMessage> messages = statusMessages.getKeyedMessages("lastName");
      assertNotNull(messages);
      assertEquals(messages.size(), 1);
      assertSame(messages.get(0).getSeverity(), StatusMessage.Severity.WARN);
      assertNotNull(statusMessages.getKeyedMessages("lastName"));
      assertNotNull(statusMessages.getKeyedMessages("username"));
   }

   /**
    * Test that the serverity is processed correctly when adding a message to a client id.
    */
   @Test
   public void testAddInfoMessageToControlFromTemplate()
   {
      StatusMessages statusMessages = getStatusMessagesInstance();
      statusMessages.addToControl("password", StatusMessage.Severity.INFO, "Good strength!");
      List<StatusMessage> messages = statusMessages.getKeyedMessages("password");
      assertNotNull(messages);
      assertEquals(messages.size(), 1);
      assertSame(StatusMessage.Severity.INFO, messages.get(0).getSeverity());
   }

   /**
    * Test that global and keyed messages can be cleared separately.
    */
   @Test
   public void testClearMessages()
   {
      StatusMessages statusMessages = getStatusMessagesInstance();

      statusMessages.add("An info message");
      statusMessages.add(StatusMessage.Severity.ERROR, "An error message");
      statusMessages.addToControl("username", "Available!");
      statusMessages.addToControl("username", "Nice choice");
      statusMessages.addToControl("password", StatusMessage.Severity.WARN, "Too short");

      assertEquals(statusMessages.getGlobalMessages().size(), 2);
      assertEquals(statusMessages.getKeyedMessages().size(), 2);

      statusMessages.clearGlobalMessages();

      assertEquals(statusMessages.getGlobalMessages().size(), 0);
      assertEquals(statusMessages.getKeyedMessages().size(), 2);

      statusMessages.clearKeyedMessages("username");

      assertEquals(statusMessages.getKeyedMessages().size(), 1);
      assertNull(statusMessages.getKeyedMessages("username"));

      statusMessages.clearKeyedMessages();

      assertEquals(statusMessages.getKeyedMessages().size(), 0);

      statusMessages.add("An info message");
      statusMessages.addToControl("username", "Nice choice");

      assertEquals(statusMessages.getGlobalMessages().size(), 1);
      assertEquals(statusMessages.getKeyedMessages().size(), 1);

      statusMessages.clear();
      
      assertEquals(statusMessages.getGlobalMessages().size(), 0);
      assertEquals(statusMessages.getKeyedMessages().size(), 0);
   }

   private StatusMessages getStatusMessagesInstance()
   {
      return new StatusMessages(new IdentityInterpolator());
   }

   // QUESTION should I make this a @Mock Interpolator and use Web Beans to test?
   private class IdentityInterpolator extends Interpolator
   {
      @Override
      public String interpolate(String string, Object... params)
      {
         return string;
      }
   }

   private class ConstraintViolationStub implements ConstraintViolation<Object>
   {
      private String message;
      private String propertyPath;

      public ConstraintViolationStub(String message, String propertyPath)
      {
         this.message = message;
         this.propertyPath = propertyPath;
      }

      public String getMessage()
      {
         return message;
      }

      public String getMessageTemplate()
      {
         throw new UnsupportedOperationException("Not supported by stub.");
      }

      public Object getRootBean()
      {
         throw new UnsupportedOperationException("Not supported by stub.");
      }

      public Object getLeafBean()
      {
         throw new UnsupportedOperationException("Not supported by stub.");
      }

      public String getPropertyPath()
      {
         return propertyPath;
      }

      public Object getInvalidValue()
      {
         throw new UnsupportedOperationException("Not supported by stub.");
      }

      public ConstraintDescriptor<?> getConstraintDescriptor()
      {
         throw new UnsupportedOperationException("Not supported by stub.");
      }

      public Class<Object> getRootBeanClass()
      {
         throw new UnsupportedOperationException("Not supported by stub.");
      }

   }
}
