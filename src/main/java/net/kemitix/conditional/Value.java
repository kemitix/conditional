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

import java.util.Optional;
import java.util.function.Supplier;

/**
 * A Value from an if-then-else in a functional-style.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public interface Value {

    /**
     * Return one of two values depending on the value of a clause.
     *
     * @param clause        The deciding clause
     * @param trueSupplier  The supplier to provide the value when the clause is true
     * @param falseSupplier The supplier to provide the value when the clause is false
     * @param <T>           The type of the value
     * @return the value from either the trueSupplier or the falseSupplier
     */
    static <T> T where(
            final boolean clause,
            final Supplier<T> trueSupplier,
            final Supplier<T> falseSupplier
    ) {
        return Value.<T>where(clause)
                .then(trueSupplier)
                .otherwise(falseSupplier);
    }

    /**
     * Return one of two values depending on the value of a clause.
     *
     * @param clause        The deciding clause
     * @param trueSupplier  The supplier to provide the value when the clause is true
     * @param falseSupplier The supplier to provide the value when the clause is false
     * @param <T>           The type of the value
     * @return the value from either the trueSupplier or the falseSupplier
     */
    static <T> T where(
            final Condition clause,
            final Supplier<T> trueSupplier,
            final Supplier<T> falseSupplier
    ) {
        return Value.<T>where(clause.isTrue(), trueSupplier, falseSupplier);
    }

    /**
     * Return an Optional either containing a value, if the clause is true, or empty.
     *
     * @param clause       The deciding clause
     * @param trueSupplier The supplier to provide the value when the clause is true
     * @param <T>          The type of the value
     * @return an Optional either containing the value from the trueSupplier or empty
     */
    static <T> Optional<T> where(
            final boolean clause,
            final Supplier<T> trueSupplier
    ) {
        return Optional.ofNullable(Value.where(clause, trueSupplier, () -> null));
    }

    /**
     * Return an Optional either containing a value, if the clause is true, or empty.
     *
     * @param clause       The deciding clause
     * @param trueSupplier The supplier to provide the value when the clause is true
     * @param <T>          The type of the value
     * @return an Optional either containing the value from the trueSupplier or empty
     */
    static <T> Optional<T> where(
            final Condition clause,
            final Supplier<T> trueSupplier
    ) {
        return Value.where(clause.isTrue(), trueSupplier);
    }

    /**
     * Create a new {@link ValueClause} for the clause.
     *
     * @param clause the condition to test
     * @param <T>    the type of the value
     * @return a true or false value clause
     */
    @SuppressWarnings("unchecked")
    static <T> ValueClause<T> where(final boolean clause) {
        if (clause) {
            return (ValueClause<T>) TrueValueClause.TRUE;
        }
        return (ValueClause<T>) FalseValueClause.FALSE;
    }

    /**
     * Create a new {@link ValueClause} for the clause.
     *
     * @param clause the condition to test
     * @param <T>    the type of the value
     * @return a true or false value clause
     */
    @SuppressWarnings("unchecked")
    static <T> ValueClause<T> where(final Condition clause) {
        return Value.where(clause.isTrue());
    }

    /**
     * Create a new {@link ValueClause} for the boolean opposite of the clause.
     *
     * @param clause the condition to test
     * @param <T>    the type of the value
     * @return a true or false value clause
     * @deprecated use {@link #where(boolean)}.{@link ValueClause#not()}
     */
    @Deprecated
    static <T> ValueClause<T> whereNot(final boolean clause) {
        return Value.<T>where(clause).not();
    }

    /**
     * An intermediate state in determining the final {@link Value}.
     *
     * @param <T> the type of the value
     */
    /* default */ interface ValueClause<T> {

        /**
         * Negate the Value.
         *
         * @return a new ValueClause with a negated value
         */
        ValueClause<T> not();

        /**
         * Create a {@link ValueSupplier} with the {@link Supplier} should the {@link ValueClause} be true.
         *
         * @param trueSupplier the Supplier for the true value
         * @return the value supplier
         */
        ValueSupplier<T> then(Supplier<T> trueSupplier);

        /**
         * Logically AND combine the current {@link ValueClause} with clause.
         *
         * @param clause the condition to test
         * @return a true or false value clause
         */
        ValueClause<T> and(Supplier<Boolean> clause);

        /**
         * Logically OR combine the current {@link ValueClause} with clause.
         *
         * @param clause the condition to test
         * @return a true or false value clause
         */
        @SuppressWarnings("PMD.ShortMethodName")
        ValueClause<T> or(Supplier<Boolean> clause);

        /**
         * Logically AND combine the current {@link ValueClause} with boolean opposite of the clause.
         *
         * @param clause the condition to test
         * @return a true or false value clause
         */
        default ValueClause<T> andNot(final Supplier<Boolean> clause) {
            return and(() -> !clause.get());
        }

        /**
         * Logically OR combine the current {@link ValueClause} with boolean opposite of the clause.
         *
         * @param clause the condition to test
         * @return a true or false value clause
         */
        default ValueClause<T> orNot(final Supplier<Boolean> clause) {
            return or(() -> !clause.get());
        }

        /**
         * An intermediate result of the {@link Value}.
         *
         * @param <T> the type of the value
         */
        /* default */ interface ValueSupplier<T> {

            /**
             * Determine the value by whether the {@link ValueClause} was true or false.
             *
             * @param falseSupplier the Supplier for the false value
             * @return the value
             */
            T otherwise(Supplier<T> falseSupplier);

            /**
             * Returns the value in an Optional if the {@link ValueClause} is true, or an empty Optional if it is false.
             *
             * @return an Optional, possibly containing the value
             */
            Optional<T> optional();
        }

    }

}
