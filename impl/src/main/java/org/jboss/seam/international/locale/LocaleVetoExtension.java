package org.jboss.seam.international.locale;

import javax.enterprise.event.Observes;
import javax.enterprise.inject.spi.Extension;
import javax.enterprise.inject.spi.ProcessAnnotatedType;

/**
 * Currently just vetoes {@link LocaleConfiguration}.
 * 
 * @author <a href="http://community.jboss.org/people/jharting">Jozef
 *         Hartinger</a>
 */
public class LocaleVetoExtension implements Extension
{
   /**
    * Veto LocaleConfiguration class from bean discovery since we want it's
    * subclasses to be beans, not the class itself.
    */
   public void vetoSeamRestConfiguration(@Observes ProcessAnnotatedType<LocaleConfiguration> event)
   {
      if (event.getAnnotatedType().getJavaClass().equals(LocaleConfiguration.class))
      {
         event.veto();
      }
   }

}
