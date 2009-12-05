package org.jboss.seam.international;

import java.util.Locale;

import org.jboss.seam.beans.Default;

public
@Default
class LocaleResolver
{
   public Locale getLocale()
   {
      return Locale.getDefault();
   }
}
