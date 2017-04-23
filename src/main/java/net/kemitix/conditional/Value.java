package net.kemitix.conditional;

import java.util.function.Supplier;

/**
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public interface Value<T> {

    static <T> ValueClause<T> where(final boolean clause) {
        if (clause) {
            return new TrueValueClause<>();
        }
        return new FalseValueClause<>();
    }

    static <T> ValueClause<T> whereNot(boolean clause) {
        return where(!clause);
    }

    interface ValueClause<T> {

        ValueSupplier<T> then(Supplier<T> trueSupplier);

        ValueClause<T> and(boolean clause);

        ValueClause<T> or(boolean clause);

        default ValueClause<T> andNot(final boolean clause) {
            return and(!clause);
        }

        default ValueClause<T> orNot(boolean clause) {
            return or(!clause);
        }

        interface ValueSupplier<T> {

            T otherwise(Supplier<T> falseSupplier);

        }

    }

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

        private class FalseValueSupplier<T> implements ValueSupplier<T> {

            @Override
            public T otherwise(final Supplier<T> falseSupplier) {
                return falseSupplier.get();
            }

        }

    }

}
