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

import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.commons.lang.ArrayUtils;

/**
 * An interceptor that chains other interceptors.
 * 
 * @author Daniel Nydegger
 */
public final class Interceptors
    implements MethodInterceptor {

    public static final MethodInterceptor EMPTY = new MethodInterceptor() {

        @Override
        public Object invoke(final MethodInvocation invocation)
            throws Throwable {
            return invocation.proceed();
        }
    };

    private final MethodInterceptor[] interceptors;

    /**
     * Creates an interceptor that chains other interceptors.
     * 
     * @param interceptors
     *            instances of {@link MethodInterceptor}.
     * @return interceptor that enclose other interceptors or Interceptors.EMPTY instance if interceptors argument is
     *         null or empty
     */
    public static MethodInterceptor create(final MethodInterceptor... interceptors) {

        if (ArrayUtils.isEmpty(interceptors)) {
            return Interceptors.EMPTY;
        }

        final List<MethodInterceptor> flatlist = new ArrayList<MethodInterceptor>();
        for (final MethodInterceptor interceptor : interceptors) {
            assert (interceptor != null);

            if (interceptor instanceof Interceptors) {
                flatlist.addAll(Arrays.asList(((Interceptors) interceptor).interceptors));
            } else if (EMPTY != interceptor) {
                flatlist.add(interceptor);
            }
        }
        if (flatlist.isEmpty()) {
            return EMPTY;
        } else if (flatlist.size() == 1) {
            return flatlist.get(0);
        }
        return new Interceptors(flatlist.toArray(new MethodInterceptor[flatlist.size()]));
    }

    private Interceptors(final MethodInterceptor[] interceptors) {
        this.interceptors = interceptors;
    }

    @Override
    public Object invoke(final MethodInvocation invocation)
        throws Throwable {
        MethodInvocation nextInvocation = invocation;

        // chain the method invocation of interceptors in reverse order
        for (int i = interceptors.length - 1; i >= 0; i--) {
            final MethodInterceptor interceptor = interceptors[i];
            final MethodInvocation previousInvocation = nextInvocation;
            nextInvocation = new MethodInvocation() {

                @Override
                public Object proceed()
                    throws Throwable {
                    return interceptor.invoke(previousInvocation);
                }

                @Override
                public Object getThis() {
                    return invocation.getThis();
                }

                @Override
                public AccessibleObject getStaticPart() {
                    return invocation.getStaticPart();
                }

                @Override
                public Object[] getArguments() {
                    return invocation.getArguments();
                }

                @Override
                public Method getMethod() {
                    return invocation.getMethod();
                }
            };
        }
        return nextInvocation.proceed();
    }
}
