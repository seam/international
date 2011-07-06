Running the Testsuite
--------------------
The testsuite module contains several submodules including the following:

* "smoke" -- core tests that should be run as part of every build of i18n. Failures here will fail the build.
* "api" -- tests of features that involve end user use of the public i18n API. Should be run with no failures before any major commits.
* "cluster" -- tests of i18n in a cluster. Should be run with no failures before any major commits.
* "internals" -- tests of i18n internals. Should be run with no failures before any major commits.
* "benchmark" -- tests used to compare performance against other releases or previous builds
* "stress" -- tests of the server's ability to perform properly while under stress 

To run the basic testsuite including smoke tests from the root directory, run "mvn test":

For basic smoke tests, simply: "mvn test"

For benchmark tests: "mvn test -Pbenchmark-tests"

For stress tests: "mvn test -Pstress-tests"

To run all the tests

> $ mvn test -PallTests