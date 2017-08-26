package net.kemitix.conditional;

import lombok.val;
import org.junit.Test;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class ValueTest {

    private static final String TRUE = "true";

    private static final String FALSE = "false";

    @Test
    public void valueWhereClauseIsTrueTypeSafe() {
        //when
        final String result = Value.where(true, () -> TRUE, () -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereClauseIsFalseTypeSafe() {
        //when
        final String result = Value.where(false, () -> TRUE, () -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereClauseIsTrueIsOptional() {
        //when
        final Optional<String> result = Value.where(true, () -> TRUE);
        //then
        assertThat(result).contains(TRUE);
    }

    @Test
    public void valueWhereClauseIsFalseIsEmptyOptional() {
        //when
        final Optional<String> result = Value.where(false, () -> TRUE);
        //then
        assertThat(result).isEmpty();
    }

    @Test
    public void valueWhereClauseIsTrue() {
        //when
        val result = Value.<String>where(true).then(() -> TRUE)
                                              .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereClauseIsFalse() {
        //when
        val result = Value.<String>where(false).then(() -> TRUE)
                                               .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereTrueAndTrueIsTrue() {
        //when
        val result = Value.<String>where(true).and(true)
                                              .then(() -> TRUE)
                                              .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereTrueAndFalseIsFalse() {
        //when
        val result = Value.<String>where(true).and(false)
                                              .then(() -> TRUE)
                                              .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereFalseAndTrueIsFalse() {
        //when
        val result = Value.<String>where(false).and(true)
                                               .then(() -> TRUE)
                                               .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereFalseAndFalseIsFalse() {
        //when
        val result = Value.<String>where(false).and(false)
                                               .then(() -> TRUE)
                                               .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereTrueOrTrueIsTrue() {
        //when
        val result = Value.<String>where(true).or(true)
                                              .then(() -> TRUE)
                                              .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereTrueOrFalseIsTrue() {
        //when
        val result = Value.<String>where(true).or(false)
                                              .then(() -> TRUE)
                                              .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereFalseOrTrueIsTrue() {
        //when
        val result = Value.<String>where(false).or(true)
                                               .then(() -> TRUE)
                                               .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereFalseOrFalseIsFalse() {
        //when
        val result = Value.<String>where(false).or(false)
                                               .then(() -> TRUE)
                                               .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereNotTrueIsFalse() {
        //when
        val result = Value.<String>whereNot(true).then(() -> TRUE)
                                                 .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereNotFalseIsTrue() {
        //when
        val result = Value.<String>whereNot(false).then(() -> TRUE)
                                                  .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereTrueAndNotTrueIsFalse() {
        //when
        val result = Value.<String>where(true).andNot(true)
                                              .then(() -> TRUE)
                                              .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereTrueAndNotFalseIsTrue() {
        //when
        val result = Value.<String>where(true).andNot(false)
                                              .then(() -> TRUE)
                                              .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereFalseAndNotTrueIsFalse() {
        //when
        val result = Value.<String>where(false).andNot(true)
                                               .then(() -> TRUE)
                                               .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereFalseAndNotFalseIsFalse() {
        //when
        val result = Value.<String>where(false).andNot(false)
                                               .then(() -> TRUE)
                                               .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereTrueOrNotTrueIsTrue() {
        //when
        val result = Value.<String>where(true).orNot(true)
                                              .then(() -> TRUE)
                                              .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereTrueOrNotFalseIsTrue() {
        //when
        val result = Value.<String>where(true).orNot(false)
                                              .then(() -> TRUE)
                                              .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }

    @Test
    public void valueWhereFalseOrNotTrueIsFalse() {
        //when
        val result = Value.<String>where(false).orNot(true)
                                               .then(() -> TRUE)
                                               .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(FALSE);
    }

    @Test
    public void valueWhereFalseOrNotFalseIsTrue() {
        //when
        val result = Value.<String>where(false).orNot(false)
                                               .then(() -> TRUE)
                                               .otherwise(() -> FALSE);
        //then
        assertThat(result).isEqualTo(TRUE);
    }
}
