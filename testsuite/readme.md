#Seam International Test Suite

##Running the testsuite on the default container (Embedded Weld)

    mvn clean verify 

##Running the testsuite on JBoss AS 7

    mvn clean verify -Darquillian=jbossas-managed-7

##Running the testsuite with a specific version of JBoss AS 7

    mvn clean verify -Darquillian=jbossas-managed-7 -Djbossas7.version=7.0.1.Final

