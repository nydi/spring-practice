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

/**
 * Changes a {@link ThreadLocal} value during {@link #invoke(MethodInvocation)}.
 * 
 * @param <T>
 *            type of {@link ThreadLocal}
 * @author Daniel Nydegger
 */
public class ThreadLocalInterceptor<T>
    implements MethodInterceptor {

    private final ThreadLocal<T> threadLocal;
    private final T value;

    /**
     * Creates a {@link ThreadLocalInterceptor} to hold the value in threadLocale during
     * {@link #invoke(MethodInvocation)}.
     * 
     * @param <T>
     *            type of {@link ThreadLocal}
     * @param threadLocal
     *            instance of {@link ThreadLocal} must not null
     * @param value
     *            can be null
     * @return newly created instance of {@link ThreadLocalInterceptor}
     */
    public static <T> ThreadLocalInterceptor<T> create(final ThreadLocal<T> threadLocal, final T value) {
        return new ThreadLocalInterceptor<T>(threadLocal, value);
    }

    /**
     * Creates and invoke a {@link ThreadLocalInterceptor}
     * 
     * @see #create(ThreadLocal, Object)
     * @param invocation
     *            invocation of the current method call
     * @return result of the invocation
     * @throws Throwable
     *             any thrown error during the invocation
     */
    public static <T> Object invoke(final ThreadLocal<T> threadLocal, final T value, final MethodInvocation invocation)
        throws Throwable {
        return create(threadLocal, value).invoke(invocation);
    }

    private ThreadLocalInterceptor(final ThreadLocal<T> threadLocal, final T value) {
        assert (null != threadLocal);

        this.threadLocal = threadLocal;
        this.value = value;
    }

    @Override
    public Object invoke(final MethodInvocation invocation)
        throws Throwable {
        // save back old value to respect multiple invoke calls during the same invocation.
        final T old = threadLocal.get();
        threadLocal.set(value);
        try {
            return invocation.proceed();
        }
        finally {
            threadLocal.set(old);
        }
    }
}
