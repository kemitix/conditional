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

import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.Supplier;

/**
 * An intermediate state where the clause has evaluated to true.
 *
 * @param <T> the type of the value
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
class TrueValueClause<T> implements Value.ValueClause<T> {

    protected static final Value.ValueClause<?> TRUE = new TrueValueClause<>();

    @Override
    public Value.ValueClause<T> not() {
        return Value.where(false);
    }

    @Override
    public ValueSupplier<T> then(final Supplier<T> trueSupplier) {
        return new TrueValueSupplier<>(trueSupplier);
    }

    @Override
    public Value.ValueClause<T> and(final Supplier<Boolean> clause) {
        return Value.where(clause.get());
    }

    @Override
    @SuppressWarnings("PMD.ShortMethodName")
    public Value.ValueClause<T> or(final Supplier<Boolean> clause) {
        return this;
    }

    /**
     * An intermediate result of the {@link Value} where the clause has evaluated to true.
     *
     * @param <T> the type of the value
     */
    @RequiredArgsConstructor
    private static final class TrueValueSupplier<T> implements ValueSupplier<T> {

        @SuppressWarnings("PMD.BeanMembersShouldSerialize")
        private final Supplier<T> valueSupplier;

        @Override
        public T otherwise(final Supplier<T> falseSupplier) {
            return valueSupplier.get();
        }

        @Override
        public Optional<T> optional() {
            return Optional.ofNullable(valueSupplier.get());
        }

    }

}
