/*
 * Copyright 2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package ch.nydi.aop.interceptor;

import java.util.concurrent.TimeUnit;

/**
 * Simple chronometer.
 * 
 * @author Daniel Nydegger
 */
public class Chronometer {

    private boolean stopped = false;
    private long start;
    private long stop;

    /**
     * Starts the chronometer.
     */
    public Chronometer() {
        start();
    }

    private void start() {
        start = System.nanoTime();
        stopped = false;
    }

    /**
     * Stops the chronometer.
     */
    public void stop() {
        if (stopped) {
            throw new IllegalStateException("chronometer is already stopped");
        }
        stop = System.nanoTime();
        stopped = true;
    }

    /**
     * Gets chronometer time in the given time unit.
     * 
     * @param unit
     *            time unit
     * @return chronometer time
     */
    public long getTime(TimeUnit unit) {
        return unit.convert(nanos(), TimeUnit.NANOSECONDS);
    }

    protected long nanos() {
        if (!stopped) {
            throw new IllegalStateException("chronometer is running, call stop() before get the time");
        }
        return (stop - start);
    }
}
