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
package ch.nydi.service.test;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * Calculates the BMI index of a person. Uses a {@link Calculator} for do division, OK thats a little bit overengineered
 * ;-), it's only used to support tests with injected beans.
 * 
 * @author Daniel Nydegger
 */
@Named
public class BMICalculatorImpl
    implements BMICalculator {

    public static final String COMPONENT_NAME = "service.BMICalculator";

    private Calculator calculator;

    @Inject
    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }

    @Override
    public Double calculate(final double weigth, final double heigth) {
        return calculator.divide(weigth, (heigth * heigth));
    }
}
