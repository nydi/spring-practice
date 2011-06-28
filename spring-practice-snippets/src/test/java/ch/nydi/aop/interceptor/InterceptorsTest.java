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

import ch.nydi.service.test.BMICalculator;
import ch.nydi.spring.TestSupport;

import javax.inject.Inject;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * TODO : Description
 * 
 * @author Daniel Nydegger
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { TestSupport.SPRING_CONFIG_SNIPPETS_2 })
public class InterceptorsTest {

    @Inject
    private BMICalculator bmiCalculator;

    @Test
    public void testInvocationWithConfiguredInterceptors() {
        final Double bmi = bmiCalculator.calculate(70d, 1.80d);
        Assert.assertNotNull(bmi);
    }
}
