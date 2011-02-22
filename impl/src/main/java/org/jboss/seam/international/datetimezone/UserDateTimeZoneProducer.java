/*
 * JBoss, Home of Professional Open Source
 * Copyright 2010, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package org.jboss.seam.international.datetimezone;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.Alter;
import org.jboss.seam.solder.core.Client;
import org.joda.time.DateTimeZone;

/**
 * DateTimeZone for a User Session. Defaults to the DateTimeZone within DefaultDateTimeZone and is altered when it receives the @Alter
 * event.
 * 
 * @author Ken Finnigan
 */

@SessionScoped
public class UserDateTimeZoneProducer implements Serializable {
    private static final long serialVersionUID = -9008203923830420841L;

    @Produces
    @Client
    @Named
    private DateTimeZone userDateTimeZone;

    @Inject
    public void init(DateTimeZone defaultDateTimeZone) {
        this.userDateTimeZone = defaultDateTimeZone;
    }

    public void alterTimeZone(@Observes @Alter @Client DateTimeZone tz) {
        this.userDateTimeZone = tz;
    }
}
