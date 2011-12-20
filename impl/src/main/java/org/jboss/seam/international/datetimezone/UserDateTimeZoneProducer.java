/*
 * JBoss, Home of Professional Open Source
 * Copyright 2011, Red Hat, Inc., and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.seam.international.datetimezone;

import java.io.Serializable;

import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;
import javax.inject.Named;

import org.jboss.seam.international.Alter;
import org.jboss.solder.core.Client;
import org.jboss.solder.core.Requires;
import org.joda.time.DateTimeZone;

/**
 * DateTimeZone for a User Session. Defaults to the DateTimeZone within DefaultDateTimeZone and is altered when it receives the @Alter
 * event.
 *
 * @author Ken Finnigan
 */

@Requires({"org.joda.time.DateTimeZone"})
@SessionScoped
public class UserDateTimeZoneProducer implements Serializable {
    private static final long serialVersionUID = -9008203923830420841L;

    @Produces
    @Named
    @Client
    private DateTimeZone userDateTimeZone;

    @Inject
    public void init(DateTimeZone defaultDateTimeZone) {
        this.userDateTimeZone = defaultDateTimeZone;
    }

    public void alterTimeZone(@Observes @Alter @Client DateTimeZone tz) {
        this.userDateTimeZone = tz;
    }
}
