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
        thenTheThenResponseRuns();
    }

    @Test
    public void whereFalseOtherwiseRuns() {
        //when
        when(false);
        //then
        thenTheOtherwiseResponseRuns();
    }

    @Test
    public void whereTrueAndTrueThenRuns() {
        //when
        when(true, true);
        //then
        thenTheThenResponseRuns();
    }

    @Test
    public void whereTrueAndFalseThenOtherwiseRuns() {
        //when
        when(true, false);
        //then
        thenTheOtherwiseResponseRuns();
    }

    @Test
    public void whereFalseAndTrueThenOtherwiseRuns() {
        //when
        when(false, true);
        //then
        thenTheOtherwiseResponseRuns();
    }

    @Test
    public void whereFalseAndFalseThenOtherwiseRuns() {
        //when
        when(false, false);
        //then
        thenTheOtherwiseResponseRuns();
    }

    @Test
    public void whereTrueThenDoSomethingAndThenDoSomethingElse() {
        //when
        Condition.where(true)
                 .then(thenResponse)
                 .and(true)
                 .then(otherwiseResponse);
        //then
        thenBothResponsesRun();
    }

    @Test
    public void whereTrueOrTrueThenDoSomething() {
        //when
        whenOr(true, true);
        //then
        thenTheThenResponseRuns();
    }

    @Test
    public void whereTrueOrFalseThenDoSomething() {
        //when
        whenOr(true, false);
        //then
        thenTheThenResponseRuns();
    }

    @Test
    public void whereFalseOrTrueThenDoSomething() {
        //when
        whenOr(false, true);
        //then
        thenTheThenResponseRuns();
    }

    @Test
    public void whereFalseOrFalseThenDoSomethingElse() {
        //when
        whenOr(false, false);
        //then
        thenTheOtherwiseResponseRuns();
    }

    @Test
    public void whereNotFalseThenRuns() {
        //when
        Condition.whereNot(false)
                 .then(thenResponse);
        //then
        thenTheThenResponseRuns();
    }

    @Test
    public void whereTrueAndNotFalseThenRuns() {
        //when
        Condition.where(true)
                 .andNot(false)
                 .then(thenResponse);
        //then
        thenTheThenResponseRuns();
    }

    @Test
    public void whereFalseOrNotFalseThenRuns() {
        //when
        Condition.where(false)
                 .orNot(false)
                 .then(thenResponse);
        //then
        thenTheThenResponseRuns();
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

    private void thenBothResponsesRun() {
        assertThat(thenFlag).isTrue();
        assertThat(otherwiseFlag).isTrue();
    }

    private void thenTheThenResponseRuns() {
        assertThat(thenFlag).isTrue();
        assertThat(otherwiseFlag).isFalse();
    }

    private void thenTheOtherwiseResponseRuns() {
        assertThat(thenFlag).isFalse();
        assertThat(otherwiseFlag).isTrue();
    }

    private void when(final boolean firstClause, final boolean secondClause) {
        Condition.where(firstClause)
                 .and(secondClause)
                 .then(thenResponse)
                 .otherwise(otherwiseResponse);
    }

}
