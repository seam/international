package org.jboss.seam.international;

import static org.testng.Assert.assertEquals;

import java.util.ResourceBundle;

import javax.el.CompositeELResolver;
import javax.enterprise.inject.AnnotationLiteral;

import org.jboss.seam.el.Expressions;
import org.jboss.seam.el.ExpressionsProducer;
import org.jboss.seam.resources.DefaultResourceLoader;
import org.jboss.seam.resources.ResourceLoaderProducer;
import org.jboss.testharness.impl.packaging.Artifact;
import org.jboss.testharness.impl.packaging.Classes;
import org.jboss.webbeans.el.WebBeansELResolver;
import org.jboss.webbeans.log.LoggerProducer;
import org.jboss.webbeans.test.AbstractWebBeansTest;
import org.testng.annotations.Test;

@Artifact
@Classes({LoggerProducer.class, DefaultResourceLoader.class, ResourceLoaderProducer.class, Expressions.class, ExpressionsProducer.class})
public class AutoInterpolatedMessagesTest extends AbstractWebBeansTest
{
   private boolean elResolverInitialized = false;
   
   @Override
   public void beforeMethod()
   {
      super.beforeMethod();
      if (!elResolverInitialized)
      {
         installWebBeansELResolver();
      }
   }
   
   // FIXME broken
   //@Test
   public void testLoadMessages()
   {
      ResourceBundle messages = getMessages();
      assertEquals(messages.getString("title"), "Hello Seam!");
      assertEquals(messages.getString("tagline"), "We put the rich in Java EE");
   }
   
   private void installWebBeansELResolver()
   {
      Expressions expressions = getCurrentManager().getInstanceByType(Expressions.class);
      // FIXME wow this is a hack to add a new ELResolver
      //((CompositeELResolver) expressions.getELContext().getELResolver()).add(new WebBeansELResolver());
      elResolverInitialized = true;
   }
   
   private ResourceBundle getMessages()
   {
      return getCurrentManager().getInstanceByType(ResourceBundle.class, new AnnotationLiteral<AutoInterpolatedMessages>() {});
   }
}
