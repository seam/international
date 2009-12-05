package org.jboss.seam.international;

import java.util.Locale;

import javax.inject.Inject;
import javax.enterprise.inject.Produces;
import javax.inject.Named;

/**
 * Producer component for the current locale. This base
 * implementation simply returns the server default
 * locale.
 * 
 * @author Gavin King
 */
public class LocaleProducer
{   
   public
   @Produces
   //@Named("org.jboss.seam.international.locale")
   Locale getLocale(LocaleResolver localeResolver)
   {
      return localeResolver.getLocale();
   }
}
