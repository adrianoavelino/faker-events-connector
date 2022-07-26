package br.com.fec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


class FecConfigTest {
    @Test
    @DisplayName("Should have default value for non required property")
    void ShouldHaveDefaultValueForNonRequiredProperty() {
        Map<String, String> props = new HashMap<>();
        FecConfig config = new FecConfig(props);
        String defaultValue = "Hello World!";
        Assertions.assertEquals(defaultValue, config.getString(FecConfig.MESSAGE_VALUE));
    }

    @Test
    @DisplayName("Should define the Value for non required property")
    void shouldDefineTheValueForNonRequiredProperty() {
        Map<String, String> props = new HashMap<>();
        props.put(FecConfig.MESSAGE_VALUE, "my message");
        FecConfig config = new FecConfig(props);
        Assertions.assertEquals("my message", config.getString(FecConfig.MESSAGE_VALUE));
    }

}