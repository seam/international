package org.jboss.seam.international.timezone;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * User TimeZone
 *
 * @author Ken Finnigan
 */
@Target( { METHOD, PARAMETER, FIELD })
@Retention(RUNTIME)
@Documented
@Qualifier
@Inherited
public @interface UserTimeZone
{
}
