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
package org.jboss.seam.international.status.builder;

import java.util.Arrays;

import org.jboss.seam.international.status.Level;
import org.jboss.seam.international.status.Message;
import org.jboss.seam.international.status.MessageBuilder;
import org.jboss.seam.international.status.MessageImpl;
import org.jboss.seam.international.status.MutableMessage;

/**
 * @author <a href="mailto:lincolnbaxter@gmail.com">Lincoln Baxter, III</a>
 * @author <a href="mailto:ssachtleben@gmail.com">Sebastian Sachtleben</a>
 */
public class TemplateMessageImpl implements MessageBuilder, TemplateMessage {
    private final Interpolator interpolator = new Interpolator();

    private String summary;
    private String detail;
    private Object[] summaryParams;
    private Object[] detailParams;

    private String targets;
    private Level level;

    public Message build() {
        MutableMessage message = new MessageImpl();

        message.setLevel(level);
        message.setText(interpolator.populate(summary, summaryParams));
        if (detail != null && !detail.equals("")) {
            message.setDetail(interpolator.populate(detail, detailParams));
        }
        message.setTargets(targets);

        return message;
    }

    public TemplateMessageImpl text(final String summary) {
        this.summary = summary;
        return this;
    }

    public TemplateMessageImpl detail(final String detail) {
        this.detail = detail;
        return this;
    }

    public TemplateMessageImpl textParams(final Object... summaryParams) {
        this.summaryParams = summaryParams;
        return this;
    }

    public TemplateMessageImpl detailParams(final Object... detailParams) {
        this.detailParams = detailParams;
        return this;
    }

    public TemplateMessage targets(final String targets) {
        this.targets = targets;
        return this;
    }

    public TemplateMessage level(final Level level) {
        this.level = level;
        return this;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((level == null) ? 0 : level.hashCode());
        result = prime * result + ((summary == null) ? 0 : summary.hashCode());
        result = prime * result + ((detail == null) ? 0 : detail.hashCode());
        result = prime * result + Arrays.hashCode(summaryParams);
        result = prime * result + Arrays.hashCode(detailParams);
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
        TemplateMessageImpl other = (TemplateMessageImpl) obj;
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
        if (!Arrays.equals(summaryParams, other.summaryParams)) {
            return false;
        }
        if (!Arrays.equals(detailParams, other.detailParams)) {
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
}
