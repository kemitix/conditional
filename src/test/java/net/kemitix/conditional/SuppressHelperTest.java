package net.kemitix.conditional;

import org.assertj.core.api.WithAssertions;
import org.junit.Test;

import java.lang.reflect.Constructor;

public class SuppressHelperTest implements WithAssertions {

    @Test
    public void utilityClassCannotBeInstantiate() throws NoSuchMethodException {
        final Constructor<SuppressHelper> constructor = SuppressHelper.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        assertThatExceptionOfType(UnsupportedOperationException.class)
                .isThrownBy(() -> new SuppressHelper());
    }

}