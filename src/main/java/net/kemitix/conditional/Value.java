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
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public interface Value {

    /**
     * Create a new {@link ValueClause} for the clause.
     *
     * @param clause the condition to test
     * @param <T>    the type of the value
     *
     * @return a true or false value clause
     */
    @SuppressWarnings({"unchecked", "avoidinlineconditionals"})
    static <T> ValueClause<T> where(final boolean clause) {
        return (ValueClause<T>) (clause ? TrueValueClause.TRUE : FalseValueClause.FALSE);
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

}
