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
package org.jboss.seam.international.status;

import java.io.Serializable;

/**
 * An object representing a message that needs to be displayed to the User.
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * @author <a href="mailto:ssachtleben@gmail.com">Sebastian Sachtleben</a>
 */
public interface Message extends Serializable {
    /**
     * Get the {@link Level} representing the severity of this message.
     */
    Level getLevel();

    /**
     * Get the message text.
     */
    String getText();

    /**
     * Get message detail.
     */
    String getDetail();

    /**
     * Get the targets for which a given view-layer or consumer should display this message, or to which this message should be
     * attached.
     */
    String getTargets();
}
