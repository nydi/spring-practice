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
package ch.nydi.spring.inject.primary;

import ch.nydi.spring.TestSupport;
import ch.nydi.spring.context.support.TestGenericXmlContextLoader;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Functional test to show the injection behavior if multiple implementations of one interface loaded in spring
 * application context. A more detailed description about this example you will find at
 * http://develop.nydi.ch/2010/12/spring-primary-bean-injection
 * 
 * @author Daniel Nydegger
 */
@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = { TestSupport.SPRING_CONFIG_SNIPPETS_1 })
@ContextConfiguration(locations = { TestSupport.SPRING_CONFIG_SNIPPETS_1 }, loader = TestGenericXmlContextLoader.class)
public class PrimarySupportTest
    implements ApplicationContextAware {

    private BaseService baseService;
    private SingleService singleService;
    private InheritedService inheritedService;
    private ApplicationContext applicationContext;

    @Inject
    public void setSingleService(SingleService singleService) {
        this.singleService = singleService;
    }

    @Inject
    public void setBaseService(BaseService baseService) {
        this.baseService = baseService;
    }

    @Inject
    public void setInheritedService(InheritedService inheritedService) {
        this.inheritedService = inheritedService;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
        throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Test
    public void testInjectedSingleService() {
        Assert.assertNotNull("singleService is null", singleService);
        Assert.assertEquals("wrong instance", SingleServiceImpl_1.class.getName(), singleService.getClass().getName());
    }

    @Test
    public void testInjectedBaseAndInheritedServices() {
        Assert.assertNotNull("baseService is null", baseService);
        Assert.assertEquals("wrong instance", BaseServiceImpl.class.getName(), baseService.getClass().getName());
        Assert.assertNotNull("inheritedService is null", inheritedService);
        Assert.assertEquals("wrong instance", InheritedServiceImpl.class.getName(),
            inheritedService.getClass().getName());
    }

    @Test
    public void testSingleServiceWithGetBeanClassAndName() {
        SingleService contextSingleService = applicationContext.getBean("singleServiceImpl_1", SingleService.class);
        Assert.assertNotNull("contextSingleService is null", contextSingleService);
        Assert.assertEquals("wrong instance", SingleServiceImpl_1.class.getName(),
            contextSingleService.getClass().getName());

        contextSingleService = applicationContext.getBean("singleServiceImpl_2", SingleService.class);
        Assert.assertNotNull("contextSingleService is null", contextSingleService);
        Assert.assertEquals("wrong instance", SingleServiceImpl_2.class.getName(),
            contextSingleService.getClass().getName());
    }

    @Test
    public void testBaseAndInheritedServiceWithGetBeanClassAndName() {
        final BaseService contextBaseService = applicationContext.getBean("baseServiceImpl", BaseService.class);
        Assert.assertNotNull("contextBaseService is null", contextBaseService);
        Assert.assertEquals("wrong instance", BaseServiceImpl.class.getName(), contextBaseService.getClass().getName());

        final InheritedService inheritedContextService =
            applicationContext.getBean("inheritedServiceImpl", InheritedService.class);
        Assert.assertNotNull("inheritedContextService is null", inheritedContextService);
        Assert.assertEquals("wrong instance", InheritedServiceImpl.class.getName(),
            inheritedContextService.getClass().getName());
    }

    // works with loader = TestGenericXmlContextLoader.class
    @Test
    public void testSingleServiceWithGetBeanClass() {
        // SingleServiceImpl_1 is annotated with @Primary so SingleServiceImpl_1
        // instance should returned here
        final SingleService contextSingleService = applicationContext.getBean(SingleService.class);
        Assert.assertNotNull("contextSingleService is null", contextSingleService);
        Assert.assertEquals("wrong instance", SingleServiceImpl_1.class.getName(),
            contextSingleService.getClass().getName());
    }

    // works with loader = TestGenericXmlContextLoader.class
    @Test
    public void testServiceWithGetBeanClass() {
        // BaseServiceImpl is annotated with @Primary so BaseServiceImpl
        // instance should returned here
        final BaseService contextBaseService = applicationContext.getBean(BaseService.class);
        Assert.assertNotNull("contextBaseService is null", contextBaseService);
        Assert.assertEquals("wrong instance", BaseServiceImpl.class.getName(), contextBaseService.getClass().getName());

        final InheritedService inheritedContextService = applicationContext.getBean(InheritedService.class);
        Assert.assertNotNull("inheritedContextService is null", inheritedContextService);
        Assert.assertEquals("wrong instance", InheritedServiceImpl.class.getName(),
            inheritedContextService.getClass().getName());
    }
}
