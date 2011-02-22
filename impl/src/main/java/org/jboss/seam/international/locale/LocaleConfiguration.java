package org.jboss.seam.international.locale;

import java.util.HashSet;
import java.util.Set;

import org.jboss.seam.solder.core.Veto;

/**
 * The locale configuration provides a list of locales defined in the seam-beans.xml.
 * 
 * @author <a href="http://community.jboss.org/people/ssachtleben">Sebastian Sachtleben</a>
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 */
@Veto
public class LocaleConfiguration {
    private Set<String> supportedLocaleKeys = new HashSet<String>();

    public Set<String> getSupportedLocaleKeys() {
        return supportedLocaleKeys;
    }

    public void addSupportedLocaleKey(String supportedLocaleKey) {
        supportedLocaleKeys.add(supportedLocaleKey);
    }

    public void setSupportedLocaleKeys(Set<String> supportedLocaleKeys) {
        this.supportedLocaleKeys = supportedLocaleKeys;
    }
}
