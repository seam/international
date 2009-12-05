package org.jboss.seam.international;

import javax.enterprise.inject.Produces;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.jboss.seam.beans.RuntimeBeanSelector;

public class LocaleResolverProducer extends RuntimeBeanSelector<LocaleResolver>
{
   /**
    * Package level constructor for unit testing
    */
   LocaleResolverProducer() {}
   
   public @Inject LocaleResolverProducer(BeanManager manager)
   {
      super(manager);
   }
   
   @Override
   public Class<LocaleResolver> getType()
   {
      return LocaleResolver.class;
   }

   @Override
   public
   @Produces
   LocaleResolver selectImplementation()
   {
      return super.selectImplementation();
   }
   
}
