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
package ch.nydi.spring.context.support;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Overrides <code>createBeanFactory()</code> to load a {@link PrimaryResolverListableBeanFactory}.
 * 
 * @author Daniel Nydegger
 */
public class PrimaryResolverClassPathXmlApplicationContext extends ClassPathXmlApplicationContext {

    public PrimaryResolverClassPathXmlApplicationContext() {
        super();
    }

    public PrimaryResolverClassPathXmlApplicationContext(ApplicationContext parent) {
        super(parent);
    }

    public PrimaryResolverClassPathXmlApplicationContext(String path, Class<?> clazz)
        throws BeansException {
        super(path, clazz);
    }

    public PrimaryResolverClassPathXmlApplicationContext(String... configLocations)
        throws BeansException {
        super(configLocations);
    }

    public PrimaryResolverClassPathXmlApplicationContext(String configLocation)
        throws BeansException {
        super(configLocation);
    }

    public PrimaryResolverClassPathXmlApplicationContext(String[] configLocations, ApplicationContext parent)
        throws BeansException {
        super(configLocations, parent);
    }

    public PrimaryResolverClassPathXmlApplicationContext(String[] configLocations, boolean refresh,
        ApplicationContext parent)
        throws BeansException {
        super(configLocations, refresh, parent);
    }

    public PrimaryResolverClassPathXmlApplicationContext(String[] configLocations, boolean refresh)
        throws BeansException {
        super(configLocations, refresh);
    }

    public PrimaryResolverClassPathXmlApplicationContext(String[] paths, Class<?> clazz, ApplicationContext parent)
        throws BeansException {
        super(paths, clazz, parent);
    }

    public PrimaryResolverClassPathXmlApplicationContext(String[] paths, Class<?> clazz)
        throws BeansException {
        super(paths, clazz);
    }

    @Override
    protected DefaultListableBeanFactory createBeanFactory() {
        return new PrimaryResolverListableBeanFactory(getInternalParentBeanFactory());
    }
}
