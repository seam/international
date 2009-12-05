package org.jboss.seam.international;

import java.util.Enumeration;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.enterprise.inject.AnnotationLiteral;
import javax.enterprise.inject.spi.BeanManager;

import org.jboss.seam.beans.BeanManagerHelper;
import org.jboss.seam.bridge.ManagerBridge;

/**
 * QUESTION should this be in the faces module (since it's JSF that needs this workaround)
 * 
 * @author Dan Allen
 */
public class SeamResourceBundleAdapter extends ResourceBundle
{
   @Override
   public Enumeration<String> getKeys()
   {
      return getMessages().getKeys();
   }

   @Override
   protected Object handleGetObject(String key)
   {
      try
      {
         return getMessages().getObject(key);
      }
      catch (MissingResourceException mre)
      {
         // fear not, the superclass will throw MissingResourceException if null is returned
      }

      return null;
   }

   protected ResourceBundle getMessages()
   {
       return BeanManagerHelper.getInstanceByType(getCurrentManager(), ResourceBundle.class, new AnnotationLiteral<AutoInterpolatedMessages>(){});      
   }

   protected BeanManager getCurrentManager()
   {
      return ManagerBridge.getProvider().getCurrentManager();
   }
}
