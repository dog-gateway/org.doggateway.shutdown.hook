/*
 * Dog - Core
 * 
 * Copyright (c) 2019 - Dario Bonino
 * 
 * Freely adapted from StackOverflow suggestions by
 * Neil Bartlett - https://stackoverflow.com/users/318921/neil-bartlett
 * BJ Hargrave - https://github.com/bjhargrave
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License
 */

package org.doggateway.shutdown.hook;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

/**
 * A simple bundle activator that does nothing but registering a shutdown hook
 * to correctly handle SIGTERM sent to a running instance of Dog, e.g., when
 * installed as a service.
 * 
 * @author Dario Bonino, dario.bonino@gmail.com
 *
 */
public class Activator implements BundleActivator
{
    // the bundle context
    private static BundleContext context;

    /**
     * Called on bundle activation
     */
    public void start(BundleContext bundleContext) throws Exception
    {
        // get the bundle context
        Activator.context = bundleContext;

        // define a shutdown thread to call on JVM shutdown
        System.out.println("Installing shutdown hook.");
        Runtime.getRuntime().addShutdownHook(new ShutdownThread(context));
    }

    public void stop(BundleContext bundleContext) throws Exception
    {
        Activator.context = null;
    }

}
