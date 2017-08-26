package net.kemitix.conditional;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class ConditionalTest {

    private Runnable thenResponse;

    private Runnable otherwiseResponse;

    private boolean thenFlag;

    private boolean otherwiseFlag;

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
                 .and(true)
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
        Condition.whereNot(false)
                 .then(thenResponse);
        //then
        assertThatTheThenResponseRuns();
    }

    @Test
    public void whereNotTrueThenOtherwiseRuns() {
        //when
        Condition.whereNot(true)
                 .then(thenResponse)
                 .otherwise(otherwiseResponse);
        //then
        assertThatTheOtherwiseResponseRuns();
    }

    @Test
    public void whereTrueAndNotFalseThenRuns() {
        //when
        Condition.where(true)
                 .andNot(false)
                 .then(thenResponse);
        //then
        assertThatTheThenResponseRuns();
    }

    @Test
    public void whereTrueAndNotTrueThenOtherwiseRuns() {
        //when
        Condition.where(true)
                 .andNot(true)
                 .then(thenResponse)
                 .otherwise(otherwiseResponse);
        //then
        assertThatTheOtherwiseResponseRuns();
    }

    @Test
    public void whereFalseOrNotFalseThenRuns() {
        //when
        Condition.where(false)
                 .orNot(false)
                 .then(thenResponse);
        //then
        assertThatTheThenResponseRuns();
    }

    @Test
    public void whereFalseOrNotTrueThenOtherwiseRuns() {
        //when
        Condition.where(false)
                 .orNot(true)
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
                 .otherwise(true)
                 .then(otherwiseResponse);
        //then
        assertThatTheOtherwiseResponseRuns();
    }

    @Test
    public void whereFalseElseFalseThenNothingRuns() {
        //when
        Condition.where(false)
                 .then(thenResponse)
                 .otherwise(false)
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

    private void whenOr(final boolean firstClause, final boolean secondClause) {
        Condition.where(firstClause)
                 .or(secondClause)
                 .then(thenResponse)
                 .otherwise(otherwiseResponse);
    }

    private void when(final boolean clause) {
        Condition.where(clause)
                 .then(thenResponse)
                 .otherwise(otherwiseResponse);
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

    private void when(final boolean firstClause, final boolean secondClause) {
        Condition.where(firstClause)
                 .and(secondClause)
                 .then(thenResponse)
                 .otherwise(otherwiseResponse);
    }

}
