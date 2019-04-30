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

import org.osgi.framework.BundleContext;
import org.osgi.framework.launch.Framework;

/**
 * A Thread that shuts down the OSGi framework in which it is running.
 * 
 * @author bonino
 *
 */
public class ShutdownThread extends Thread
{
    private int shutdownWaitTime = 5000;
    private BundleContext context;

    /**
     * Create a new {@link ShutdownThread} using the given {@link BundleContext}
     * to access the OSGi system bundle.
     * 
     * @param context
     *            The {@link BundleContext} used to shutdown the framework.
     */
    public ShutdownThread(BundleContext context)
    {
        super();

        // store the bundle context
        this.context = context;

        // get the shutdown wait time
        String shutdownTimeout = System
                .getProperty("org.doggateway.shutdown.hook.timeout");

        if (shutdownTimeout != null && !shutdownTimeout.isEmpty())
        {
            try
            {
                this.shutdownWaitTime = Integer
                        .parseInt(shutdownTimeout.trim());
            }
            catch (NumberFormatException nfe)
            {
                // use defualt value
                this.shutdownWaitTime = 5000;
            }
        }
    }

    @Override
    public void run()
    {
        System.out.println(
                "JVM Shutdown hook invoked, stopping the Dog gateway.");
        try
        {
            // get the system bundle
            Framework systemBundle = context.getBundle(0)
                    .adapt(Framework.class);
            // stop the bundle
            systemBundle.stop();
            // log the fact that the shutdown thread is waiting for a clean OSGi
            // shutdown
            System.out.println(
                    "Waiting up to "+this.shutdownWaitTime+"ms for OSGi shutdown to complete...");
            // wait for the framework shutdown for 5S
            systemBundle.waitForStop(shutdownWaitTime);
        }
        catch (Exception e)
        {
            System.err.println("Failed to cleanly shutdown OSGi Framework: "
                    + e.getMessage());
            e.printStackTrace();
        }
    }

}
