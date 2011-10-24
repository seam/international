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
package org.jboss.seam.international.test.status;

import org.jboss.seam.international.status.Status;
import org.jboss.solder.messages.Locale;
import org.jboss.solder.messages.Message;
import org.jboss.solder.messages.MessageBundle;

@MessageBundle
public interface RegistrationStatusMessages {
    @Status
    @Message("Username {0} is already taken. Please choose another.")
    @Locale("en_US")
    String duplicateUsername(String username);
}
