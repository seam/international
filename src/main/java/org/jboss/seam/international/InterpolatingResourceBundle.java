package org.jboss.seam.international;

import java.util.Enumeration;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class InterpolatingResourceBundle extends ResourceBundle
{
   private ResourceBundle delegate;
   private Interpolator interpolator;

   public InterpolatingResourceBundle(ResourceBundle delegate, Interpolator interpolator)
   {
      this.delegate = delegate;
      this.interpolator = interpolator;
   }

   @Override
   public Locale getLocale()
   {
      return delegate.getLocale();
   }

   @Override
   public Enumeration<String> getKeys()
   {
      return delegate.getKeys();
   }

   @Override
   protected Object handleGetObject(String key)
   {
      try
      {
         return interpolate(delegate.getObject(key));
      }
      catch (MissingResourceException mre)
      {
         // superclass will throw MissingResourceException if null is returned
      }

      return null;
   }

   protected Object interpolate(Object message)
   {
      if (message != null && message instanceof String)
      {
         return interpolator.interpolate((String) message);
      }
      else
      {
         return message;
      }
   }

}
