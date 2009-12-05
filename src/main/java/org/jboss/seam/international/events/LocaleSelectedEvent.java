package org.jboss.seam.international.events;

/**
 * This event is raised when a locale is selected
 *  
 * @author Shane Bryzak
 */
public class LocaleSelectedEvent
{
   private String locale;
   
   public LocaleSelectedEvent(String locale)
   {
      this.locale = locale;
   }
   
   public String getLocale()
   {
      return locale;
   }
}
