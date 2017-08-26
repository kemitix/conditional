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
 * An intermediate state where the clause has evaluated to false.
 *
 * @param <T> the type of the value
 *
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
class FalseValueClause<T> implements Value.ValueClause<T> {

    protected static final Value.ValueClause FALSE = new FalseValueClause();

    @Override
    public ValueSupplier<T> then(final Supplier<T> trueSupplier) {
        return new FalseValueSupplier();
    }

    @Override
    public Value.ValueClause<T> and(final boolean clause) {
        return this;
    }

    @Override
    public Value.ValueClause<T> or(final boolean clause) {
        return Value.where(clause);
    }

    /**
     * An intermediate result of the {@link Value} where the clause has evaluated to false.
     */
    private class FalseValueSupplier implements ValueSupplier<T> {

        @Override
        public T otherwise(final Supplier<T> falseSupplier) {
            return falseSupplier.get();
        }

    }

}
