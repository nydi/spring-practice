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

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Logs the the arguments and the return value of a method invocation.
 * 
 * @author Daniel Nydegger
 */
public class TraceInterceptor
    implements MethodInterceptor {

    private final Logger logger = LoggerFactory.getLogger(TimerInterceptor.class);

    @Override
    public Object invoke(MethodInvocation invocation)
        throws Throwable {

        final String prefix = "invocation arguments of " + invocation.getMethod().getName() + " ";

        final Object[] arguments = invocation.getArguments();
        if (ArrayUtils.isEmpty(arguments)) {
            logger.info(prefix + "{no arguments}");
        } else {
            final StringBuilder flattenArguments = new StringBuilder();
            flattenArguments.append("{");
            for (int i = 0; i < arguments.length; i++) {
                if (i > 0) {
                    flattenArguments.append(" | ");
                }
                flattenArguments.append(flattenArgument(arguments[i]));
            }
            flattenArguments.append("}");
            logger.info(prefix + flattenArguments.toString());

        }

        Object invocationResult = null;

        try {
            invocationResult = invocation.proceed();
        } catch (final Throwable e) {
            logger.info("execption occured, no return value");
            throw e;
        }
        finally {

            final StringBuilder builder = new StringBuilder();
            builder.append("invocation return value of ").append(invocation.getMethod().getName()).append(": {").append(
                flattenArgument(invocationResult)).append("}");
            logger.info(builder.toString());
        }

        return invocationResult;
    }

    private String flattenArgument(Object argument) {
        if (null == argument) {
            return "null";
        }

        if (argument instanceof Object[]) {
            if (ArrayUtils.isEmpty((Object[]) argument)) {
                return "no arguments";
            }
            return StringUtils.join((Object[]) argument, ", ");
        }
        return argument.toString();
    }
}
