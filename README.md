# org.doggateway.shutdown.hook
Shutdown hook for Dog. Enables clean exit of Dog on SIGTERM

This bundle enables Dog to react to SIGTERM signals by adding a JVM shutdown hook, which attempts to cleanly shutdown Dog by stopping the system bundle. The hook waits up to ```org.doggateway.shutdown.hook.timeout``` milliseconds for the framework to cleanly stop, then allows the JVM to simply exit (abruptly).
The timeout to wait for a clean shutdown can be defined by launching the Dog gateway with the following parameter

```
-Dorg.doggateway.shutdown.hook.timeout=<timeout-in-milliseconds>
```
for example

```
-Dorg.doggateway.shutdown.hook.timeout=3000
```

or by adding the same parameter in the ```config.ini``` file of the Dog standalone distribution.
