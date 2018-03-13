/**
 * The MIT License (MIT)
 *
 * Copyright (c) 2017 Paul Campbell
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without restriction,
 * including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies
 * or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,
 * INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.kemitix.conditional;

import java.util.function.Supplier;

/**
 * If-then-else in a functional-style.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public interface Condition {

    /**
     * Create a new {@code Condition} for the clause.
     *
     * @param clause the condition to test
     *
     * @return the Condition
     */
    static Condition where(final boolean clause) {
        if (clause) {
            return TrueCondition.TRUE;
        }
        return FalseCondition.FALSE;
    }

    /**
     * Create a new {@code Condition} for the boolean opposite of the clause.
     *
     * @param clause the condition to test
     *
     * @return the Condition
     */
    static Condition whereNot(final boolean clause) {
        return where(!clause);
    }

    /**
     * Logically AND combine the current {@code Condition} with the clause.
     *
     * @param clause the condition to test
     *
     * @return the Condition
     */
    Condition and(Supplier<Boolean> clause);

    /**
     * Logically AND combine the current {@code Condition} with boolean opposite of the clause.
     *
     * @param clause the condition to test
     *
     * @return the Condition
     */
    default Condition andNot(final Supplier<Boolean> clause) {
        return and(() -> !clause.get());
    }

    /**
     * Logically OR combine the current {@code Condition} with the clause.
     *
     * @param clause the condition to test
     *
     * @return the Condition
     */
    @SuppressWarnings("PMD.ShortMethodName")
    Condition or(Supplier<Boolean> clause);

    /**
     * Logically OR combine the current {@code Condition} with the boolean opposite of the clause.
     *
     * @param clause the condition to test
     *
     * @return the Condition
     */
    default Condition orNot(final Supplier<Boolean> clause) {
        return or(() -> !clause.get());
    }

    /**
     * Perform this response if the {@code Condition} is {@code true}.
     *
     * @param response the response to perform
     *
     * @return the Condition
     */
    Condition then(Action response);

    /**
     * Perform this response if the {@code Condition} is {@code false}.
     *
     * @param response the response to perform
     */
    void otherwise(Action response);

    /**
     * Create a new {@code Condition} for the clause as a continuation to an existing {@code Condition}.
     *
     * @param clause the condition to test
     *
     * @return the Condition
     */
    default Condition otherwise(final Supplier<Boolean> clause) {
        return where(clause.get());
    }

}
