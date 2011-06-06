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
package ch.nydi.spring.config;

import ch.nydi.spring.TestSupport;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Functional test to show the features of {@link SystemPropertiesFactoryBean}. A more detailed description about this
 * example you will find at http://develop.nydi.ch/2010/12/spring-multiple-properties
 * 
 * @author Daniel Nydegger
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { TestSupport.SPRING_CONFIG_SNIPPETS_1 })
public class SystemPropertyFactoryTest {

    @Inject
    private ConfigValueBean configValueBean;

    @Test
    public void testConfigValues() {
        // check for values
        Assert.assertNotNull(configValueBean.getWelcome());
        Assert.assertNotNull(configValueBean.getDbUser());
        Assert.assertNotNull(configValueBean.getDbPassword());
        Assert.assertNotNull(configValueBean.getUserPath());

        // check system property values
        Assert.assertEquals(configValueBean.getWelcome(), System.getProperty("msg.welcome"));
        Assert.assertEquals(configValueBean.getDbUser(), System.getProperty("database.user"));
        Assert.assertEquals(configValueBean.getDbPassword(), System.getProperty("database.password"));
        Assert.assertEquals(configValueBean.getUserPath(), System.getProperty("my.tmp.dir"));
    }
}
