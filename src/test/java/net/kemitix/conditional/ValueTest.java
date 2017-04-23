package net.kemitix.conditional;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Paul Campbell (pcampbell@kemitix.net).
 */
public class ValueTest {

    private static final String TRUE = "true";

    private static final String FALSE = "false";

    @Test
    public void valueWhereTrueIsTrue() {
        //when
        final String result = Value.<String>where(true).then(this::isTrue)
                                                       .otherwise(this::isFalse);
        //then
        thenIsTrue(result);
    }

    @Test
    public void valueWhereFalseIsFalse() {
        //when
        final String result = Value.<String>where(false).then(this::isTrue)
                                                        .otherwise(this::isFalse);
        //then
        thenIsFalse(result);
    }

    @Test
    public void valueWhereTrueAndTrueIsTrue() {
        //when
        final String result = Value.<String>where(true).and(true)
                                                       .then(this::isTrue)
                                                       .otherwise(this::isFalse);
        //then
        thenIsTrue(result);
    }

    @Test
    public void valueWhereTrueAndFalseIsFalse() {
        //when
        final String result = Value.<String>where(true).and(false)
                                                       .then(this::isTrue)
                                                       .otherwise(this::isFalse);
        //then
        thenIsFalse(result);
    }

    @Test
    public void valueWhereFalseAndTrueIsFalse() {
        //when
        final String result = Value.<String>where(false).and(true)
                                                        .then(this::isTrue)
                                                        .otherwise(this::isFalse);
        //then
        thenIsFalse(result);
    }

    @Test
    public void valueWhereFalseAndFalseIsFalse() {
        //when
        final String result = Value.<String>where(false).and(false)
                                                        .then(this::isTrue)
                                                        .otherwise(this::isFalse);
        //then
        thenIsFalse(result);
    }

    @Test
    public void valueWhereTrueOrTrueIsTrue() {
        //when
        final String result = Value.<String>where(true).or(true)
                                                       .then(this::isTrue)
                                                       .otherwise(this::isFalse);
        //then
        thenIsTrue(result);
    }

    @Test
    public void valueWhereTrueOrFalseIsTrue() {
        //when
        final String result = Value.<String>where(true).or(false)
                                                       .then(this::isTrue)
                                                       .otherwise(this::isFalse);
        //then
        thenIsTrue(result);
    }

    @Test
    public void valueWhereFalseOrTrueIsTrue() {
        //when
        final String result = Value.<String>where(false).or(true)
                                                        .then(this::isTrue)
                                                        .otherwise(this::isFalse);
        //then
        thenIsTrue(result);
    }

    @Test
    public void valueWhereFalseOrFalseIsFalse() {
        //when
        final String result = Value.<String>where(false).or(false)
                                                        .then(this::isTrue)
                                                        .otherwise(this::isFalse);
        //then
        thenIsFalse(result);
    }

    @Test
    public void valueWhereNotTrueIsFalse() {
        //when
        final String result = Value.<String>whereNot(true).then(this::isTrue)
                                                          .otherwise(this::isFalse);
        //then
        thenIsFalse(result);
    }

    @Test
    public void valueWhereNotFalseIsTrue() {
        //when
        final String result = Value.<String>whereNot(false).then(this::isTrue)
                                                           .otherwise(this::isFalse);
        //then
        thenIsTrue(result);
    }

    @Test
    public void valueWhereTrueAndNotTrueIsFalse() {
        //when
        final String result = Value.<String>where(true).andNot(true)
                                                       .then(this::isTrue)
                                                       .otherwise(this::isFalse);
        //then
        thenIsFalse(result);
    }

    @Test
    public void valueWhereTrueAndNotFalseIsTrue() {
        //when
        final String result = Value.<String>where(true).andNot(false)
                                                       .then(this::isTrue)
                                                       .otherwise(this::isFalse);
        //then
        thenIsTrue(result);
    }

    @Test
    public void valueWhereFalseAndNotTrueIsFalse() {
        //when
        final String result = Value.<String>where(false).andNot(true)
                                                        .then(this::isTrue)
                                                        .otherwise(this::isFalse);
        //then
        thenIsFalse(result);
    }

    @Test
    public void valueWhereFalseAndNotFalseIsFalse() {
        //when
        final String result = Value.<String>where(false).andNot(false)
                                                        .then(this::isTrue)
                                                        .otherwise(this::isFalse);
        //then
        thenIsFalse(result);
    }

    @Test
    public void valueWhereTrueOrNotTrueIsTrue() {
        //when
        final String result = Value.<String>where(true).orNot(true)
                                                       .then(this::isTrue)
                                                       .otherwise(this::isFalse);
        //then
        thenIsTrue(result);
    }

    @Test
    public void valueWhereTrueOrNotFalseIsTrue() {
        //when
        final String result = Value.<String>where(true).orNot(false)
                                                       .then(this::isTrue)
                                                       .otherwise(this::isFalse);
        //then
        thenIsTrue(result);
    }

    @Test
    public void valueWhereFalseOrNotTrueIsFalse() {
        //when
        final String result = Value.<String>where(false).orNot(true)
                                                        .then(this::isTrue)
                                                        .otherwise(this::isFalse);
        //then
        thenIsFalse(result);
    }

    @Test
    public void valueWhereFalseOrNotFalseIsTrue() {
        //when
        final String result = Value.<String>where(false).orNot(false)
                                                        .then(this::isTrue)
                                                        .otherwise(this::isFalse);
        //then
        thenIsTrue(result);
    }

    private String isTrue() {
        return TRUE;
    }

    private String isFalse() {
        return FALSE;
    }

    private void thenIsFalse(final String result) {
        assertThat(result).isEqualTo(isFalse());
    }

    private void thenIsTrue(final String result) {
        assertThat(result).isEqualTo(TRUE);
    }

}
