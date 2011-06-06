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

import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Helper class to replace system property values in a string.
 * 
 * @author Daniel Nydegger
 */
public class PropertyVariableParser {

    private final Logger log = LoggerFactory.getLogger(PropertyVariableParser.class);

    /**
     * Replaces variables with the pattern ${aSystemPropertyValue}.
     * 
     * @param propertyValue
     *            a string with the pattern ${asystem.property.value}
     * @return replacement of propertyValue with the value of System.getEnv(propertyValue);
     */
    public String replaceVariables(String propertyValue) {
        final Scanner scanner = new Scanner(propertyValue);
        scanner.useDelimiter("\\$\\{");

        while (scanner.hasNext()) {
            String propertyName = scanner.next();

            final int endindex = propertyName.indexOf('}');

            if (-1 != endindex) {
                propertyName = propertyName.substring(0, endindex);
            } else {
                continue;
            }

            final String systemPropertyValue = System.getProperty(propertyName);

            if (null == systemPropertyValue) {
                log.warn("no such system property: " + propertyName);
            } else {
                propertyValue = propertyValue.replace("${" + propertyName + "}", systemPropertyValue);
            }
        }

        return propertyValue;
    }
}
