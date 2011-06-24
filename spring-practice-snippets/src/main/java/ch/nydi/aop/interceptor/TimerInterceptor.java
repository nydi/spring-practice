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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Measures the time of a invocation in nanoseconds.
 * 
 * @author Daniel Nydegger
 */
public class TimerInterceptor
    implements MethodInterceptor {

    private final Logger logger = LoggerFactory.getLogger(TraceInterceptor.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public Object invoke(MethodInvocation invocation)
        throws Throwable {

        final Chronometer chronometer = new Chronometer();

        Object retVal = null;

        try {
            retVal = invocation.proceed();
        }
        finally {
            chronometer.stop();
            final StringBuilder builder = new StringBuilder();
            builder.append("duration of ").append(invocation.getMethod().getName()).append(" [ns]: ").append(
                chronometer.getTime(TimeUnit.NANOSECONDS));
            logger.info(builder.toString());
        }

        return retVal;
    }
}
