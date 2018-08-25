package net.kemitix.conditional;

import org.assertj.core.api.WithAssertions;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class ConditionalTest implements WithAssertions {

    private Action thenResponse;
    private Action otherwiseResponse;
    private boolean thenFlag;
    private boolean otherwiseFlag;
    private final org.assertj.core.api.Condition<? super Condition> trueCondition =
            new org.assertj.core.api.Condition<>(Condition::isTrue, "is true");
    private final org.assertj.core.api.Condition<? super Condition> falseCondition =
            new org.assertj.core.api.Condition<>(Condition::isFalse, "is false");

    @Before
    public void setUp() {
        thenResponse = () -> thenFlag = true;
        otherwiseResponse = () -> otherwiseFlag = true;
    }

    @Test
    public void whereTrueThenRuns() {
        //when
        when(true);
        //then
        assertThatTheThenResponseRuns();
    }

    @Test
    public void whereFalseOtherwiseRuns() {
        //when
        when(false);
        //then
        assertThatTheOtherwiseResponseRuns();
    }

    @Test
    public void whereTrueAndTrueThenRuns() {
        //when
        when(true, true);
        //then
        assertThatTheThenResponseRuns();
    }

    @Test
    public void whereTrueAndFalseThenOtherwiseRuns() {
        //when
        when(true, false);
        //then
        assertThatTheOtherwiseResponseRuns();
    }

    @Test
    public void whereFalseAndTrueThenOtherwiseRuns() {
        //when
        when(false, true);
        //then
        assertThatTheOtherwiseResponseRuns();
    }

    @Test
    public void whereFalseAndFalseThenOtherwiseRuns() {
        //when
        when(false, false);
        //then
        assertThatTheOtherwiseResponseRuns();
    }

    @Test
    public void whereTrueThenDoSomethingAndThenDoSomethingElse() {
        //when
        Condition.where(true)
                .then(thenResponse)
                .and(() -> true)
                .then(otherwiseResponse);
        //then
        assertThatBothResponsesRun();
    }

    @Test
    public void whereTrueOrTrueThenDoSomething() {
        //when
        whenOr(true, true);
        //then
        assertThatTheThenResponseRuns();
    }

    @Test
    public void whereTrueOrFalseThenDoSomething() {
        //when
        whenOr(true, false);
        //then
        assertThatTheThenResponseRuns();
    }

    @Test
    public void whereFalseOrTrueThenDoSomething() {
        //when
        whenOr(false, true);
        //then
        assertThatTheThenResponseRuns();
    }

    @Test
    public void whereFalseOrFalseThenDoSomethingElse() {
        //when
        whenOr(false, false);
        //then
        assertThatTheOtherwiseResponseRuns();
    }

    @Test
    public void whereNotFalseThenRuns() {
        //when
        Condition.where(false).not()
                .then(thenResponse);
        //then
        assertThatTheThenResponseRuns();
    }

    @Test
    public void whereNotTrueThenOtherwiseRuns() {
        //when
        Condition.where(true).not()
                .then(thenResponse)
                .otherwise(otherwiseResponse);
        //then
        assertThatTheOtherwiseResponseRuns();
    }

    @Test
    public void whereTrueAndNotFalseThenRuns() {
        //when
        Condition.where(true)
                .and(() -> false).not()
                .then(thenResponse);
        //then
        assertThatTheThenResponseRuns();
    }

    @Test
    public void whereTrueAndNotTrueThenOtherwiseRuns() {
        //when
        Condition.where(true)
                .and(() -> true).not()
                .then(thenResponse)
                .otherwise(otherwiseResponse);
        //then
        assertThatTheOtherwiseResponseRuns();
    }

    @Test
    public void whereFalseOrNotFalseThenRuns() {
        //when
        Condition.where(false)
                .or(() -> false).not()
                .then(thenResponse);
        //then
        assertThatTheThenResponseRuns();
    }

    @Test
    public void whereFalseOrNotTrueThenOtherwiseRuns() {
        //when
        Condition.where(false)
                .or(() -> true).not()
                .then(thenResponse)
                .otherwise(otherwiseResponse);
        //then
        assertThatTheOtherwiseResponseRuns();
    }

    @Test
    public void whereFalseElseTrueThenOtherwiseRuns() {
        //when
        Condition.where(false)
                .then(thenResponse)
                .otherwise(() -> true)
                .then(otherwiseResponse);
        //then
        assertThatTheOtherwiseResponseRuns();
    }

    @Test
    public void whereFalseElseFalseThenNothingRuns() {
        //when
        Condition.where(false)
                .then(thenResponse)
                .otherwise(() -> false)
                .then(otherwiseResponse);
        //then
        assertThatNoResponseRuns();
    }

    @Test
    public void whereTrueChainedThensBothRuns() {
        //when
        Condition.where(true)
                .then(thenResponse)
                .then(otherwiseResponse);
        //then
        assertThatBothResponsesRun();
    }

    @Test
    public void whereFalseChainedThensNothingRuns() {
        //when
        Condition.where(false)
                .then(thenResponse)
                .then(otherwiseResponse);
        //then
        assertThatNoResponseRuns();
    }

    private void assertThatBothResponsesRun() {
        assertThatTheThenResponseRan();
        assertThatTheOtherwiseResponseRan();
    }

    private void assertThatTheThenResponseRuns() {
        assertThatTheThenResponseRan();
        assertThatTheOtherwiseResponseDidNotRun();
    }

    private void assertThatTheOtherwiseResponseRuns() {
        assertThatTheThenResponseDidNotRun();
        assertThatTheOtherwiseResponseRan();
    }

    private void assertThatTheOtherwiseResponseRan() {
        assertThat(otherwiseFlag).as("otherwise response runs")
                .isTrue();
    }

    private void assertThatTheThenResponseRan() {
        assertThat(thenFlag).as("then response runs")
                .isTrue();
    }

    private void assertThatTheOtherwiseResponseDidNotRun() {
        assertThat(otherwiseFlag).as("otherwise response does not run")
                .isFalse();
    }

    private void assertThatTheThenResponseDidNotRun() {
        assertThat(thenFlag).as("then response does not run")
                .isFalse();
    }

    private void assertThatNoResponseRuns() {
        assertThatTheThenResponseDidNotRun();
        assertThatTheOtherwiseResponseDidNotRun();
    }

    private void when(final boolean clause) {
        Condition.where(clause)
                .then(thenResponse)
                .otherwise(otherwiseResponse);
    }

    private void when(
            final boolean firstClause,
            final boolean secondClause
    ) {
        Condition.where(firstClause)
                .and(() -> secondClause)
                .then(thenResponse)
                .otherwise(otherwiseResponse);
    }

    private void whenOr(
            final boolean firstClause,
            final boolean secondClause
    ) {
        Condition.where(firstClause)
                .or(() -> secondClause)
                .then(thenResponse)
                .otherwise(otherwiseResponse);
    }

    @Test
    public void shortCurcuitOr() {
        //given
        final AtomicInteger atomicInteger = new AtomicInteger();
        //when
        Condition.where(true)
                .or(() -> atomicInteger.compareAndSet(0, 2))
                .then(thenResponse);
        //then
        assertThatTheThenResponseRan();
        assertThat(atomicInteger).hasValue(0);
    }

    @Test
    public void shortCurcuitAnd() {
        //given
        final AtomicInteger atomicInteger = new AtomicInteger();
        //when
        Condition.where(false)
                .and(() -> atomicInteger.compareAndSet(0, 2))
                .then(thenResponse);
        //then
        assertThatTheThenResponseDidNotRun();
        assertThat(atomicInteger).hasValue(0);
    }

    @Test
    public void whereTrueThenThrowException() {
        //given
        assertThatExceptionOfType(IOException.class)
                .isThrownBy(() -> Condition.where(true)
                        .thenThrow(new IOException()));
    }

    @Test
    public void whereFalseThenDoNotThrowException() throws Exception {
        assertThatCode(() ->
                Condition.where(false)
                        .thenThrow(new IOException()))
                .doesNotThrowAnyException();
    }

    @Test
    public void whereFalseOtherwiseThenThrowException() {
        //given
        assertThatExceptionOfType(IOException.class)
                .isThrownBy(() -> Condition.where(false)
                        .otherwiseThrow(new IOException()));
    }

    @Test
    public void whereTrueOtherwiseThenDoNotThrowException() throws Exception {
        assertThatCode(() ->
                Condition.where(true)
                        .otherwiseThrow(new IOException()))
                .doesNotThrowAnyException();
    }

    @Test
    public void whereTrueConditionAndTrueConditionThenTrueCondition() {
        //given
        final Condition condition1 = Condition.where(true);
        final Condition condition2 = Condition.where(true);
        //when
        final Condition result = condition1.and(condition2);
        //then
        assertThat(result).is(trueCondition);
    }

    @Test
    public void whereTrueConditionAndFalseConditionThenFalseCondition() {
        //given
        final Condition condition1 = Condition.where(true);
        final Condition condition2 = Condition.where(false);
        //when
        final Condition result = condition1.and(condition2);
        //then
        assertThat(result).is(falseCondition);
    }

    @Test
    public void whereFalseConditionAndTrueConditionThenFalseCondition() {
        //given
        final Condition condition1 = Condition.where(false);
        final Condition condition2 = Condition.where(true);
        //when
        final Condition result = condition1.and(condition2);
        //then
        assertThat(result).is(falseCondition);
    }

    @Test
    public void whereFalseConditionAndFalseConditionThenFalseCondition() {
        //given
        final Condition condition1 = Condition.where(false);
        final Condition condition2 = Condition.where(false);
        //when
        final Condition result = condition1.and(condition2);
        //then
        assertThat(result).is(falseCondition);
    }

    @Test
    public void whereTrueConditionOrTrueConditionThenTrueCondition() {
        //given
        final Condition condition1 = Condition.where(true);
        final Condition condition2 = Condition.where(true);
        //when
        final Condition result = condition1.or(condition2);
        //then
        assertThat(result).is(trueCondition);
    }

    @Test
    public void whereTrueConditionOrFalseConditionThenTrueCondition() {
        //given
        final Condition condition1 = Condition.where(true);
        final Condition condition2 = Condition.where(false);
        //when
        final Condition result = condition1.or(condition2);
        //then
        assertThat(result).is(trueCondition);
    }

    @Test
    public void whereFalseConditionOrTrueConditionThenTrueCondition() {
        //given
        final Condition condition1 = Condition.where(false);
        final Condition condition2 = Condition.where(true);
        //when
        final Condition result = condition1.or(condition2);
        //then
        assertThat(result).is(trueCondition);
    }

    @Test
    public void whereFalseConditionOrFalseConditionThenFalseCondition() {
        //given
        final Condition condition1 = Condition.where(false);
        final Condition condition2 = Condition.where(false);
        //when
        final Condition result = condition1.or(condition2);
        //then
        assertThat(result).is(falseCondition);
    }

    @Test
    public void whereTrueWhenNotThenFalse() {
        assertThat(Condition.where(true).not()).is(falseCondition);
    }

    @Test
    public void whereFalseWhenNotThenTriue() {
        assertThat(Condition.where(false).not()).is(trueCondition);
    }
}
