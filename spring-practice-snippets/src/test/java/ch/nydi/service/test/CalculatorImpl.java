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

import java.math.BigDecimal;
import java.util.Collection;

import javax.inject.Named;

/**
 * @author Daniel Nydegger
 */
@Named
public class CalculatorImpl
    implements Calculator {

    private String calculatorName;

    public void setCalculatorName(String calculatorName) {
        this.calculatorName = calculatorName;
    }

    public String getCalculatorName() {
        return calculatorName;
    }

    @Override
    public Integer add(final int a, final int b)
        throws Exception {
        return (a + b);
    }

    @Override
    public Integer add(final Collection<Integer> values) {
        int r = 0;
        for (final int value : values) {
            r += value;
        }
        return r;
    }

    @Override
    public Double divide(final double a, final double b) {
        if (b == 0) {
            throw new RuntimeException("division by 0");
        }
        return new Double(a / b);
    }

    public Integer add(BigDecimal a, BigDecimal b) {
        return null;
    }
}
