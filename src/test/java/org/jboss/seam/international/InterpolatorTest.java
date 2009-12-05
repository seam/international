package org.jboss.seam.international;

import static org.testng.Assert.assertEquals;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.el.CompositeELResolver;
import javax.el.ELContext;

import org.jboss.seam.el.AbstractELResolver;
import org.jboss.seam.el.Expressions;
import org.jboss.seam.el.ExpressionsProducer;
import org.jboss.testharness.impl.packaging.Artifact;
import org.jboss.testharness.impl.packaging.Classes;
import org.jboss.webbeans.log.LoggerProducer;
import org.jboss.webbeans.test.AbstractWebBeansTest;
import org.testng.annotations.Test;

/**
 * Verify that the {@link Interpolator} properly resolves both EL value
 * expressions and positional substitutions, while also honoring the
 * {@link Locale} returns by the {@link LocalProducer}.
 *
 * @author Dan Allen
 * @see Interpolator
 * @see Expressions
 * @see LocaleProducer
 */
@Artifact(addCurrentPackage = false)
@Classes({
   LoggerProducer.class, Interpolator.class, Expressions.class, ExpressionsProducer.class,
   LocaleProducer.class, LocaleResolver.class, LocaleResolverProducer.class
})
public class InterpolatorTest extends AbstractWebBeansTest
{
   private boolean elResolverInitialized = false;
   
   @Override
   public void beforeMethod()
   {
      super.beforeMethod();
      if (!elResolverInitialized)
      {
         installTestFixtureELResolver();
      }
   }

   // FIXME broken
   // @Test
   public void testValueExpressionInterpolation()
   {
      String result = getInterpolatorInstance().interpolate("Hey #{name}!");
      assertEquals(result, "Hey Dan!");
   }

   // FIXME broken
   //@Test
   public void testParameterSubstitution()
   {
      String result = getInterpolatorInstance().interpolate("The two planets between the Earth and the Sun are {0} and {1}.", "Mercury", "Venus");
      assertEquals(result, "The two planets between the Earth and the Sun are Mercury and Venus.");
   }

   // FIXME broken
   //@Test(expectedExceptions = IllegalArgumentException.class)
   public void testInterpolateExceedsMaxParameters()
   {
      getInterpolatorInstance().interpolate("doesn't matter", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11");
   }

   // FIXME broken
   //@Test
   public void testMultipleValueExpressionsAndParameters()
   {
      String result = getInterpolatorInstance().interpolate("#{name}, meet {0}. #0, meet #{name}. Everyone, meet {1}.", "Pete", "Gavin");
      assertEquals(result, "Dan, meet Pete. Pete, meet Dan. Everyone, meet Gavin.");
   }

   // FIXME broken
   //@Test
   public void testFormatWithProperLocale()
   {
      Locale current = Locale.getDefault();
      try {
         Calendar cal = Calendar.getInstance();
         cal.set(2009, 5, 2);
         Locale.setDefault(Locale.US);
         String result = getInterpolatorInstance().interpolate("On {0,date} there was a disturbance in Java EE.", cal.getTime());
         assertEquals(result, "On Jun 2, 2009 there was a disturbance in Java EE.");

         Locale.setDefault(Locale.FRANCE);
         result = getInterpolatorInstance().interpolate("On {0,date} there was a disturbance in Java EE.", cal.getTime());
         assertEquals(result, "On 2 juin 2009 there was a disturbance in Java EE.");
      }
      finally {
         Locale.setDefault(current);
      }
   }

   private Interpolator getInterpolatorInstance() {
      return getCurrentManager().getInstanceByType(Interpolator.class);
   }

   private void installTestFixtureELResolver()
   {
      Map<Object, Object> fixture = new HashMap<Object, Object>();
      fixture.put("name", "Dan");
      Expressions expressions = getCurrentManager().getInstanceByType(Expressions.class);
      // FIXME wow this is a hack to add a new EL resolver
      ((CompositeELResolver) expressions.getELContext().getELResolver()).add(new TestFixtureELResolver(fixture));
      elResolverInitialized = true;
   }

   public class TestFixtureELResolver extends AbstractELResolver
   {
      private Map<Object, Object> data;

      public TestFixtureELResolver(Map<Object, Object> data)
      {
         this.data = data;
      }

      @Override
      public Object getValue(ELContext context, Object base, Object property)
      {
         if (base == null && data.containsKey(property))
         {
            context.setPropertyResolved(true);
            return data.get(property);
         }

         return null;
      }
   }
}
