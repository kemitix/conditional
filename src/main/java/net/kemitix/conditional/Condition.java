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
            return new TrueCondition();
        }
        return new FalseCondition();
    }

    /**
     * Create a new {@code Condition} for the boolean opposite of the clause.
     *
     * @param clause the condition to test
     *
     * @return the Condition
     */
    static Condition whereNot(boolean clause) {
        return where(!clause);
    }

    /**
     * Logically AND combine the current {@code Condition} with the clause.
     *
     * @param clause the condition to test
     *
     * @return the Condition
     */
    Condition and(boolean clause);

    /**
     * Logically AND combine the current {@code Condition} with boolean opposite of the clause.
     *
     * @param clause the condition to test
     *
     * @return the Condition
     */
    default Condition andNot(boolean clause) {
        return and(!clause);
    }

    /**
     * Logically OR combine the current {@code Condition} with the clause.
     *
     * @param clause the condition to test
     *
     * @return the Condition
     */
    Condition or(boolean clause);

    /**
     * Logically OR combine the current {@code Condition} with the boolean opposite of the clause.
     *
     * @param clause the condition to test
     *
     * @return the Condition
     */
    default Condition orNot(boolean clause) {
        return or(!clause);
    }

    /**
     * Perform this response if the {@code Condition} is {@code true}.
     *
     * @param response the response to perform
     *
     * @return the Condition
     */
    Condition then(Runnable response);

    /**
     * Perform this response if the {@code Condition} is {@code false}.
     *
     * @param response the response to perform
     */
    void otherwise(Runnable response);

    /**
     * A {@code Condition} that has evaluated to {@code true}.
     */
    class TrueCondition implements Condition {

        @Override
        public Condition and(final boolean clause) {
            if (clause) {
                return this;
            }
            return new FalseCondition();
        }

        @Override
        public Condition or(final boolean secondClause) {
            return this;
        }

        @Override
        public Condition then(final Runnable response) {
            response.run();
            return this;
        }

        @Override
        public void otherwise(final Runnable response) {
        }

    }

    /**
     * A {@code Condition} that has evaluated to {@code false}.
     */
    class FalseCondition implements Condition {

        @Override
        public Condition and(final boolean clause) {
            return this;
        }

        @Override
        public Condition or(final boolean secondClause) {
            if (secondClause) {
                return new TrueCondition();
            }
            return this;
        }

        @Override
        public Condition then(final Runnable response) {
            return this;
        }

        @Override
        public void otherwise(final Runnable response) {
            response.run();
        }

    }

}
