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

import java.util.ArrayList;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Overrides <code>getBean()</code> to resolve primary marked beans if more than one bean found for a type.
 * 
 * @author Daniel Nydegger
 */
public class PrimaryResolverListableBeanFactory extends DefaultListableBeanFactory {

    public PrimaryResolverListableBeanFactory() {
        super();
    }

    public PrimaryResolverListableBeanFactory(BeanFactory parentBeanFactory) {
        super(parentBeanFactory);
    }

    @Override
    public <T> T getBean(Class<T> requiredType)
        throws BeansException {
        Assert.notNull(requiredType, "Required type must not be null");
        String[] beanNames = getBeanNamesForType(requiredType);
        String primaryCandidate = null;
        if (beanNames.length > 1) {
            final ArrayList<String> autowireCandidates = new ArrayList<String>();

            for (final String beanName : beanNames) {
                final BeanDefinition beanDefinition = getBeanDefinition(beanName);
                if (beanDefinition.isAutowireCandidate()) {
                    autowireCandidates.add(beanName);
                    if (beanDefinition.isPrimary()) {
                        primaryCandidate = beanName;
                    }
                }
            }
            if (autowireCandidates.size() > 0) {
                beanNames = autowireCandidates.toArray(new String[autowireCandidates.size()]);
            }
        }
        if (beanNames.length == 1) {
            return getBean(beanNames[0], requiredType);
        } else if (beanNames.length > 1) { // more than one bean defined, lookup primary candidate
            if (primaryCandidate != null) {
                return getBean(primaryCandidate, requiredType);
            }
            throw new NoSuchBeanDefinitionException(requiredType, "expected single bean but found " + beanNames.length
                + ": " + StringUtils.arrayToCommaDelimitedString(beanNames));
        } else if (beanNames.length == 0 && getParentBeanFactory() != null) {
            return getParentBeanFactory().getBean(requiredType);
        } else {
            throw new NoSuchBeanDefinitionException(requiredType, "expected single bean but found " + beanNames.length
                + ": " + StringUtils.arrayToCommaDelimitedString(beanNames));
        }
    }
}
