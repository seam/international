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
package org.jboss.seam.international.test.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.DependencyResolvers;
import org.jboss.shrinkwrap.resolver.api.maven.MavenDependencyResolver;
import org.jboss.shrinkwrap.resolver.api.maven.filter.StrictFilter;

/**
 * @author <a href="http://community.jboss.org/people/kenfinni">Ken Finnigan</a>
 * 
 */
public class Libraries {

    private static Map<String, Collection<JavaArchive>> libs = new HashMap<String, Collection<JavaArchive>>();

    public static Collection<JavaArchive> jodaLibrary() {
        String artifact = ArtifactNames.JODA_TIME;
        return getLib(artifact, true);
    }

    public static Collection<JavaArchive> jodaLibraryWithTransitives() {
        String artifact = ArtifactNames.JODA_TIME;
        return getLib(artifact, false);
    }

    public static Collection<JavaArchive> seamIntlLibrary() {
        // We need to construct seam-international manually, since MavenDependencyResolver can't read it from the Maven reactor
        // so we would get some old version from the repository...
        
        Collection<JavaArchive> libs = new LinkedList<JavaArchive> ();
        libs.add(ShrinkWrap.create(JavaArchive.class, "seam-international.jar")
            .addPackages(false,
                    org.jboss.seam.international.Alter.class.getPackage(),
                    org.jboss.seam.international.datetimezone.ForwardingDateTimeZone.class.getPackage(),
                    org.jboss.seam.international.jdktimezone.ForwardingTimeZone.class.getPackage(),
                    org.jboss.seam.international.locale.DefaultLocale.class.getPackage(),
                    org.jboss.seam.international.status.Status.class.getPackage(),
                    org.jboss.seam.international.status.builder.BundleKey.class.getPackage(),
                    org.jboss.seam.international.timezone.DefaultTimeZone.class.getPackage())
            .addAsServiceProvider(javax.enterprise.inject.spi.Extension.class, org.jboss.seam.international.status.TypedStatusMessageBundleExtension.class)
            .addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml"));

        return libs;
        /*String artifact = ArtifactNames.SEAM_INTERNATIONAL;
        return getLib(artifact, true);*/
    }

    public static Collection<JavaArchive> seamIntlLibraryWithTransitives() {
        Collection<JavaArchive> libs = new LinkedList<JavaArchive> ();
        libs.addAll(seamIntlLibrary());
        libs.addAll(solderLibraryWithTransitives());
        libs.addAll(jodaLibraryWithTransitives());
        
        return libs;
        
        /*String artifact = ArtifactNames.SEAM_INTERNATIONAL;
        return getLib(artifact, false);*/
    }

    public static Collection<JavaArchive> solderLibrary() {
        String artifact = ArtifactNames.SEAM_SOLDER;
        return getLib(artifact, true);
    }

    public static Collection<JavaArchive> solderLibraryWithTransitives() {
        String artifact = ArtifactNames.SEAM_SOLDER;
        return getLib(artifact, false);
    }

    private static Collection<JavaArchive> getLib(String artifact, boolean strictFilter) {
        if (!libs.containsKey(artifact)) {
            MavenDependencyResolver res = DependencyResolvers.use(MavenDependencyResolver.class)
                    .configureFrom("../settings.xml")
                    .loadReposFromPom("pom.xml")
                    .artifact(artifact);
            libs.put(artifact, strictFilter ? res.resolveAs(JavaArchive.class, new StrictFilter()) : res.resolveAs(JavaArchive.class));
        }
        return libs.get(artifact);
    }
}
