package org.jboss.seam.international.test.locale;

import javax.annotation.PostConstruct;

import org.jboss.seam.international.locale.LocaleConfiguration;

public class CustomLocaleConfiguration extends LocaleConfiguration {
    @PostConstruct
    public void setup() {
        addSupportedLocaleKey("en");
        addSupportedLocaleKey("fr");
    }
}
