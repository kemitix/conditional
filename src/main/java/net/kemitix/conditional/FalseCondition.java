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
 * A {@code Condition} that has evaluated to {@code false}.
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
final class FalseCondition implements Condition {

    public static final Condition FALSE = new net.kemitix.conditional.FalseCondition();

    @Override
    public Condition and(final Supplier<Boolean> clause) {
        return FALSE;
    }

    @Override
    @SuppressWarnings("PMD.ShortMethodName")
    public Condition or(final Supplier<Boolean> secondClause) {
        return Condition.where(secondClause.get());
    }

    @Override
    public Condition then(final Action response) {
        return FALSE;
    }

    @Override
    public void otherwise(final Action response) {
        response.perform();
    }

    @Override
    public void thenThrow(final Exception exception) {
        // do nothing
    }

    @Override
    public void thenThrow(final Supplier<Exception> exceptionSupplier) {
        // do nothing
    }

    @Override
    public void otherwiseThrow(final Exception exception) throws Exception {
        throw exception;
    }

    @Override
    public void otherwiseThrow(final Supplier<Exception> exceptionSupplier) throws Exception {
        throw exceptionSupplier.get();
    }

    @Override
    public boolean isTrue() {
        return false;
    }

    @Override
    public boolean isFalse() {
        return true;
    }

}
