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
 * A Value from an if-then-else in a functional-style.
 *
 * @param <T> the type of the value
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public interface Value<T> {

    /**
     * Create a new {@link ValueClause} for the clause.
     *
     * @param clause the condition to test
     * @param <T>    the type of the value
     *
     * @return a true or false value clause
     */
    static <T> ValueClause<T> where(final boolean clause) {
        if (clause) {
            return new TrueValueClause<>();
        }
        return new FalseValueClause<>();
    }

    /**
     * Create a new {@link ValueClause} for the boolean opposite of the clause.
     *
     * @param clause the condition to test
     * @param <T>    the type of the value
     *
     * @return a true or false value clause
     */
    static <T> ValueClause<T> whereNot(boolean clause) {
        return where(!clause);
    }

    /**
     * An intermediate state in determining the final {@link Value}.
     *
     * @param <T> the type of the value
     */
    interface ValueClause<T> {

        /**
         * Create a {@link ValueSupplier} with the {@link Supplier} should the {@link ValueClause} be true.
         *
         * @param trueSupplier the Supplier for the true value
         *
         * @return the value supplier
         */
        ValueSupplier<T> then(Supplier<T> trueSupplier);

        /**
         * Logically AND combine the current {@link ValueClause} with clause.
         *
         * @param clause the condition to test
         *
         * @return a true or false value clause
         */
        ValueClause<T> and(boolean clause);

        /**
         * Logically OR combine the current {@link ValueClause} with clause.
         *
         * @param clause the condition to test
         *
         * @return a true or false value clause
         */
        ValueClause<T> or(boolean clause);

        /**
         * Logically AND combine the current {@link ValueClause} with boolean opposite of the clause.
         *
         * @param clause the condition to test
         *
         * @return a true or false value clause
         */
        default ValueClause<T> andNot(final boolean clause) {
            return and(!clause);
        }

        /**
         * Logically OR combine the current {@link ValueClause} with boolean opposite of the clause.
         *
         * @param clause the condition to test
         *
         * @return a true or false value clause
         */
        default ValueClause<T> orNot(boolean clause) {
            return or(!clause);
        }

        /**
         * An intermediate result of the {@link Value}.
         *
         * @param <T> the type of the value
         */
        interface ValueSupplier<T> {

            /**
             * Determine the value by whether the {@link ValueClause} was true or false.
             *
             * @param falseSupplier the Supplier for the false value
             *
             * @return the value
             */
            T otherwise(Supplier<T> falseSupplier);

        }

    }

    /**
     * An intermediate state where the clause has evaluated to true.
     *
     * @param <T> the type of the value
     */
    class TrueValueClause<T> implements ValueClause<T> {

        @Override
        public ValueSupplier<T> then(final Supplier<T> trueSupplier) {
            return new TrueValueSupplier<T>(trueSupplier);
        }

        @Override
        public ValueClause<T> and(final boolean clause) {
            return Value.<T>where(clause);
        }

        @Override
        public ValueClause<T> or(final boolean clause) {
            return this;
        }

        /**
         * An intermediate result of the {@link Value} where the clause has evaluated to true.
         *
         * @param <T> the type of the value
         */
        private class TrueValueSupplier<T> implements ValueSupplier<T> {

            private final Supplier<T> valueSupplier;

            TrueValueSupplier(final Supplier<T> valueSupplier) {
                this.valueSupplier = valueSupplier;
            }

            @Override
            public T otherwise(final Supplier<T> falseSupplier) {
                return valueSupplier.get();
            }

        }

    }

    /**
     * An intermediate state where the clause has evaluated to false.
     *
     * @param <T> the type of the value
     */
    class FalseValueClause<T> implements ValueClause<T> {

        @Override
        public ValueSupplier<T> then(final Supplier<T> trueSupplier) {
            return new FalseValueSupplier<T>();
        }

        @Override
        public ValueClause<T> and(final boolean clause) {
            return this;
        }

        @Override
        public ValueClause<T> or(final boolean clause) {
            return Value.<T>where(clause);
        }

        /**
         * An intermediate result of the {@link Value} where the clause has evaluated to false.
         *
         * @param <T> the type of the value
         */
        private class FalseValueSupplier<T> implements ValueSupplier<T> {

            @Override
            public T otherwise(final Supplier<T> falseSupplier) {
                return falseSupplier.get();
            }

        }

    }

}
