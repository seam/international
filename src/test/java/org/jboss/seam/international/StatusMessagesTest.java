package org.jboss.seam.international;

import static org.testng.Assert.assertEquals;

import org.jboss.seam.el.Expressions;
import org.jboss.seam.el.ExpressionsProducer;
import org.jboss.seam.resources.DefaultResourceLoader;
import org.jboss.seam.resources.ResourceLoaderProducer;
import org.jboss.testharness.impl.packaging.Artifact;
import org.jboss.testharness.impl.packaging.Classes;
import org.jboss.webbeans.context.ConversationContext;
import org.jboss.webbeans.log.LoggerProducer;
import org.jboss.webbeans.test.AbstractWebBeansTest;
import org.jboss.weld.context.api.helpers.ConcurrentHashMapBeanStore;
import org.testng.annotations.Test;

/**
 * Integration test the StatusMessages bean to ensure that it finds
 * and uses the interpolator and interpolates the message templates.
 *
 * @author Dan Allen
 */
@Artifact(addCurrentPackage = false)
@Classes(
{
   LoggerProducer.class, StatusMessages.class, Interpolator.class, Expressions.class, ExpressionsProducer.class,
   LocaleProducer.class, LocaleResolver.class, LocaleResolverProducer.class, MessagesProducer.class,
   DefaultResourceLoader.class, ResourceLoaderProducer.class
})
public class StatusMessagesTest extends AbstractWebBeansTest
{
   @Override
   public void beforeMethod()
   {
      super.beforeMethod();
      activateConversationContext();
   }

   // FIXME broken
   //@Test
   public void testAddGlobalMessageFromTemplateWithInterpolations()
   {
      StatusMessages statusMessages = getStatusMessagesInstance();
      statusMessages.add("The hole numbers of Amen Corner at #{'Augusta National'} are {0}, {1} and {2}.", 11, 12, 13);
      statusMessages.onBeforeRender();
      assertEquals(statusMessages.getGlobalMessages().size(), 1);
      StatusMessage message = statusMessages.getGlobalMessages().get(0);
      assertEquals(message.getSummary(), "The hole numbers of Amen Corner at Augusta National are 11, 12 and 13.");
   }

   // TODO test loading messages from resource bundle when resource bundle is ready

   private void activateConversationContext()
   {
      // TODO fix
      //ConversationContext.instance().setBeanStore(new ConcurrentHashMapBeanStore());
      //ConversationContext.instance().setActive(true);
   }

   private StatusMessages getStatusMessagesInstance()
   {
      return getCurrentManager().getInstanceByType(StatusMessages.class);
   }
}
