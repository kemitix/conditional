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

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * If-then-else in a functional-style.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
@SuppressWarnings({"methodcount", "PMD.TooManyMethods"})
public interface Condition {

    /**
     * Create a new {@code Condition} for the clause.
     *
     * @param clause the condition to test
     * @return the Condition
     */
    static Condition where(final boolean clause) {
        if (clause) {
            return TrueCondition.TRUE;
        }
        return FalseCondition.FALSE;
    }

    /**
     * Checks if the Condition is true or not.
     *
     * @return true if the Condition is true
     */
    boolean isTrue();

    /**
     * Checks of the Condition is false or not.
     *
     * @return true if the Condition is false
     */
    boolean isFalse();

    /**
     * Negates the Condtion.
     *
     * @return a false Condition if the Condition is true, or a true Condition if the Condition is false.
     */
    default Condition not() {
        return Condition.where(isFalse());
    }

    /**
     * Logically AND combine the current {@code Condition} with the clause.
     *
     * @param clause the condition to test
     * @return the Condition
     */
    Condition and(Supplier<Boolean> clause);

    /**
     * Logicaly OR current {@code Condition} with the other {@code Condition}.
     *
     * @param other the other Condition
     * @return true if both Conditions are true
     */
    default Condition and(Condition other) {
        return Condition.where(isTrue()).and(other::isTrue);
    }

    /**
     * Logically OR combine the current {@code Condition} with the clause.
     *
     * @param clause the condition to test
     * @return the Condition
     */
    @SuppressWarnings("PMD.ShortMethodName")
    Condition or(Supplier<Boolean> clause);

    /**
     * Logically OR the current {@code Condition} with the other {@code Condition}.
     *
     * @param other the other Condition
     * @return true if either Condition is true
     */
    default Condition or(Condition other) {
        return where(isTrue()).or(other::isTrue);
    }

    /**
     * Perform this response if the {@code Condition} is {@code true}.
     *
     * @param response the response to perform
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
     * @return the Condition
     */
    default Condition otherwise(final Supplier<Boolean> clause) {
        return where(clause.get());
    }

    /**
     * Throw the exception if the {@code Condition} is {@code true}.
     *
     * @param exception the Exception to throw
     * @throws Exception the exception
     * @deprecated use {@link #thenThrow(Supplier)}
     */
    @Deprecated
    @SuppressWarnings(SuppressHelper.CS_ILLEGALTHROWS)
    void thenThrow(Exception exception) throws Exception;

    /**
     * Throw the exception supplied if the {@code Condition} is {@code true}.
     *
     * @param exceptionSupplier the supplier of the Exception to throw
     * @throws Exception the exception
     */
    @SuppressWarnings(SuppressHelper.CS_ILLEGALTHROWS)
    void thenThrow(Supplier<Exception> exceptionSupplier) throws Exception;

    /**
     * Throw then exception if the {@code Condition} is {@code false}.
     *
     * @param exception the Exception to throw
     * @throws Exception the exception
     * @deprecated use {@link #otherwiseThrow(Supplier)}
     */
    @Deprecated
    @SuppressWarnings(SuppressHelper.CS_ILLEGALTHROWS)
    void otherwiseThrow(Exception exception) throws Exception;

    /**
     * Throw then exception if the {@code Condition} is {@code false}.
     *
     * @param exceptionSupplier the supplier of the Exception to throw
     * @throws Exception the exception
     */
    @SuppressWarnings(SuppressHelper.CS_ILLEGALTHROWS)
    void otherwiseThrow(Supplier<Exception> exceptionSupplier) throws Exception;

    /**
     * Apply the function to the Condtion, resulting an another Condition.
     *
     * @param f the function to apply
     * @return a new Condition
     */
    default Condition flatMap(final Function<Boolean, Condition> f) {
        return f.apply(isTrue());
    }
}
