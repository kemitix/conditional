package net.kemitix.conditional;

import lombok.val;
import org.assertj.core.api.WithAssertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class ValueTest implements WithAssertions {

    private static final String TRUE = "true";
    private static final String FALSE = "false";

    private static final Condition TRUE_CONDITION = Condition.where(true);
    private static final Condition FALSE_CONDITION = Condition.where(false);

    @Test
    public void valueWhereClauseIsTrueTypeSafe() {
        //when
        final String result = Value.where(TRUE_CONDITION, () -> TRUE, () -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereClauseIsFalseTypeSafe() {
        //when
        final String result = Value.where(FALSE_CONDITION, () -> TRUE, () -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereClauseIsTrueIsOptional() {
        //when
        final Optional<String> result = Value.where(TRUE_CONDITION, () -> TRUE);
        //then
        assertThat(result).contains(TRUE);
    }

    @Test
    public void valueWhereClauseIsFalseIsEmptyOptional() {
        //when
        final Optional<String> result = Value.where(FALSE_CONDITION, () -> TRUE);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void valueWhereClauseIsTrue() {
        //when
        final val result = Value.<String>where(TRUE_CONDITION).then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereClauseIsFalse() {
        //when
        final val result = Value.<String>where(FALSE_CONDITION).then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereTrueAndTrueIsTrue() {
        //when
        final val result = Value.<String>where(TRUE_CONDITION).and(() -> true)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereTrueAndFalseIsFalse() {
        //when
        final val result = Value.<String>where(TRUE_CONDITION).and(() -> false)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereFalseAndTrueIsFalse() {
        //when
        final val result = Value.<String>where(FALSE_CONDITION).and(() -> true)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereFalseAndFalseIsFalse() {
        //when
        final val result = Value.<String>where(FALSE_CONDITION).and(() -> false)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereTrueOrTrueIsTrue() {
        //when
        final val result = Value.<String>where(TRUE_CONDITION).or(() -> true)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereTrueOrFalseIsTrue() {
        //when
        final val result = Value.<String>where(TRUE_CONDITION).or(() -> false)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereFalseOrTrueIsTrue() {
        //when
        final val result = Value.<String>where(FALSE_CONDITION).or(() -> true)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereFalseOrFalseIsFalse() {
        //when
        final val result = Value.<String>where(FALSE_CONDITION).or(() -> false)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereNotTrueIsFalse() {
        //when
        final val result = Value.<String>where(true).not().then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void deprecatedValueWhereNotFalseIsTrue() {
        //when
        final val result = Value.<String>where(false).not().then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereNotFalseIsTrue() {
        //when
        final val result = Value.<String>where(false).not().then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereTrueAndNotTrueIsFalse() {
        //when
        final val result = Value.<String>where(TRUE_CONDITION).and(() -> true).not()
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereTrueAndNotFalseIsTrue() {
        //when
        final val result = Value.<String>where(TRUE_CONDITION).and(() -> false).not()
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereFalseAndNotTrueIsFalse() {
        //when
        final val result = Value.<String>where(FALSE_CONDITION).and(() -> true)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereFalseAndNotFalseIsFalse() {
        //when
        final val result = Value.<String>where(FALSE_CONDITION).and(() -> false)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereTrueOrNotTrueIsTrue() {
        //when
        final val result = Value.<String>where(true).orNot(() -> true)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereTrueOrNotFalseIsTrue() {
        //when
        final val result = Value.<String>where(TRUE_CONDITION).orNot(() -> false)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void deprecatedValueWhereFalseOrNotTrueIsFalse() {
        //when
        final val result = Value.<String>where(FALSE_CONDITION).orNot(() -> true)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereFalseOrNotTrueIsFalse() {
        //when
        final val result = Value.<String>where(FALSE_CONDITION).or(() -> true).not()
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereFalseOrNotFalseIsTrue() {
        //when
        final val result = Value.<String>where(false).orNot(() -> false)
                .then(() -> TRUE)
                .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereTrueThenIsNotEmpty() {
        //given
        final Optional<Object> result = Value.where(TRUE_CONDITION).then(() -> "value").optional();
        //then
        assertThat(result).contains("value");
    }

    @Test
    public void valueWhereFalseThenIsEmpty() {
        //given
        final Optional<Object> result = Value.where(FALSE_CONDITION).then(() -> "value").optional();
        //when
        assertThat(result).isEmpty();
    }

    @Test
    public void shortCircuitOr() {
        //given
        final AtomicInteger atomicInteger = new AtomicInteger();
        //when
        final Optional<String> result = Value.<String>where(TRUE_CONDITION)
                .or(() -> atomicInteger.compareAndSet(0, 2))
                .then(() -> "Pass")
                .optional();
        //then
        assertThat(result).contains("Pass");
        assertThat(atomicInteger).hasValue(0);
    }

    @Test
    public void shortCircuitAnd() {
        //given
        final AtomicInteger atomicInteger = new AtomicInteger();
        //when
        final Optional<String> result = Value.<String>where(FALSE_CONDITION)
                .and(() -> atomicInteger.compareAndSet(0, 2))
                .then(() -> "Pass")
                .optional();
        //then
        assertThat(result).isEmpty();
        assertThat(atomicInteger).hasValue(0);
    }

    @Test
    public void deprecatedAndNot() {
        //when
        final String result = Value.<String>where(TRUE_CONDITION).andNot(() -> false)
                .then(() -> TRUE).otherwise(() -> FALSE);
        //then
        assertThat(result).isSameAs(TRUE);
    }
}
