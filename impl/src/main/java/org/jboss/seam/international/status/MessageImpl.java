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

/**
 * A basic implementation of {@link MutableMessage}.
 *
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * @author <a href="mailto:ssachtleben@gmail.com">Sebastian Sachtleben</a>
 */
public class MessageImpl implements Message, MutableMessage {
    private static final long serialVersionUID = -1812292372048679525L;

    private String summary;
    private String detail;
    private String targets;
    private Level level;

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        result = prime * result + ((summary == null) ? 0 : summary.hashCode());
        result = prime * result + ((detail == null) ? 0 : detail.hashCode());
        result = prime * result + ((targets == null) ? 0 : targets.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        MessageImpl other = (MessageImpl) obj;
        if (level == null) {
            if (other.level != null) {
                return false;
            }
        } else if (!level.equals(other.level)) {
            return false;
        }
        if (summary == null) {
            if (other.summary != null) {
                return false;
            }
        } else if (!summary.equals(other.summary)) {
            return false;
        }
        if (detail == null) {
            if (other.detail != null) {
                return false;
            }
        } else if (!detail.equals(other.detail)) {
            return false;
        }
        if (targets == null) {
            if (other.targets != null) {
                return false;
            }
        } else if (!targets.equals(other.targets)) {
            return false;
        }
        return true;
    }

    /*
     * Getters & Setters
     */
    public String getText() {
        return summary;
    }

    public String getDetail() {
        return detail;
    }

    public String getTargets() {
        return targets;
    }

    public Level getLevel() {
        return level;
    }

    public void setText(final String summary) {
        this.summary = summary;
    }

    public void setDetail(final String detail) {
        this.detail = detail;
    }

    public void setTargets(final String targets) {
        this.targets = targets;
    }

    public void setLevel(final Level level) {
        this.level = level;
    }

}
