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

import org.springframework.beans.factory.annotation.QualifierAnnotationAutowireCandidateResolver;
import org.springframework.beans.factory.support.BeanDefinitionReader;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigUtils;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.test.context.support.AbstractContextLoader;

/**
 * Similar to GenericXmlContextLoader, extend from {@link AbstractContextLoader} to override
 * <code>LoadContext()<code> for loading a {@link PrimaryResolverListableBeanFactory}.
 * 
 * @author Daniel Nydegger
 */
public class TestGenericXmlContextLoader extends AbstractContextLoader {

    /**
     * {@inheritDoc}
     */
    @Override
    public final ConfigurableApplicationContext loadContext(final String... locations)
        throws Exception {

        final DefaultListableBeanFactory beanFactory = new PrimaryResolverListableBeanFactory();
        beanFactory.setParameterNameDiscoverer(new LocalVariableTableParameterNameDiscoverer());
        beanFactory.setAutowireCandidateResolver(new QualifierAnnotationAutowireCandidateResolver());

        final GenericApplicationContext context = new GenericApplicationContext(beanFactory);
        createBeanDefinitionReader(context).loadBeanDefinitions(locations);
        AnnotationConfigUtils.registerAnnotationConfigProcessors(context);
        context.refresh();
        context.registerShutdownHook();
        return context;
    }

    private BeanDefinitionReader createBeanDefinitionReader(final GenericApplicationContext context) {
        return new XmlBeanDefinitionReader(context);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getResourceSuffix() {
        return "-context.xml";
    }
}
