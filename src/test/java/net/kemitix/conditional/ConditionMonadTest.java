package net.kemitix.conditional;

import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

public class ConditionMonadTest implements WithAssertions {

    private final boolean v = true;
    private final Function<Boolean, Condition> f = i -> r(true);
    private final Function<Boolean, Condition> g = i -> r(i);

    private static Condition r(boolean v) {
        return Condition.where(v);
    }

    @Test
    public void leftIdentity() {
        assertThat(
                r(v).flatMap(f)
        ).isEqualTo(
                f.apply(v)
        );
    }

    @Test
    public void rightIdentity() {
        assertThat(
                r(v).flatMap(x -> r(x))
        ).isEqualTo(
                r(v)
        );
    }

    @Test
    public void associativity() {
        assertThat(
                r(v).flatMap(f).flatMap(g)
        ).isEqualTo(
                r(v).flatMap(x -> f.apply(x).flatMap(g))
        );
    }

}
