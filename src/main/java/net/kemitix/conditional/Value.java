package net.kemitix.conditional;

import java.util.function.Supplier;

/**
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class Value<T> {

    private boolean clause;

    private Supplier<T> trueSupplier;

    private Value(final boolean clause) {
        this.clause = clause;
    }

    static <T> Value<T> where(final boolean clause) {
        return new Value<>(clause);
    }

    static <T> Value<T> whereNot(final boolean clause) {
        return where(!clause);
    }

    Value<T> then(final Supplier<T> trueSupplier) {
        this.trueSupplier = trueSupplier;
        return this;
    }

    T otherwise(final Supplier<T> falseSupplier) {
        if (clause) {
            return trueSupplier.get();
        }
        return falseSupplier.get();
    }

    Value<T> and(final boolean clause) {
        this.clause = this.clause && clause;
        return this;
    }

    Value<T> or(final boolean clause) {
        this.clause = this.clause || clause;
        return this;
    }

    Value<T> andNot(final boolean clause) {
        return and(!clause);
    }

    Value<T> orNot(final boolean clause) {
        return or(!clause);
    }

}
