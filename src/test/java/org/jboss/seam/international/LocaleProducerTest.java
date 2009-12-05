package org.jboss.seam.international;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertSame;

import java.util.Locale;

import org.jboss.testharness.impl.packaging.Artifact;
import org.jboss.testharness.impl.packaging.Classes;
import org.jboss.webbeans.test.AbstractWebBeansTest;
import org.testng.annotations.Test;

/**
 * Verify that the default {@link LocalProducer} returns the
 * default locale of the JVM and is produced when {@link Locale}
 * is resolved by JCDI.
 *
 * @author Dan Allen
 */
@Artifact(addCurrentPackage = false)
@Classes({LocaleProducer.class, LocaleResolver.class, LocaleResolverProducer.class})
public class LocaleProducerTest extends AbstractWebBeansTest
{
   /**
    * Ensure that the producer method run in standalone
    * mode produces the JVM default locale.
    */
   @Test
   public void testProducerMethodReturnsJvmDefault()
   {
      LocaleResolver resolver = new LocaleResolver();
      Locale result = resolver.getLocale();
      assertNotNull(result);
      assertEquals(result.toString(), Locale.getDefault().toString());
      
      LocaleProducer producer = new LocaleProducer();
      Locale result2 = producer.getLocale(resolver);
      assertSame(result2, result);
   }

   /**
    * Ensure that the manager produces the JVM default locale
    * for the type Locale.
    */
   // FIXME broken
   //@Test
   public void testProducesJvmDefault()
   {
      Locale result = getCurrentManager().getInstanceByType(Locale.class);
      assertEquals(result.toString(), Locale.getDefault().toString());
   }
}
