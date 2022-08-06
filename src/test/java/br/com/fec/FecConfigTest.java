package br.com.fec;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;


class FecConfigTest {
    @Test
    @DisplayName("Should have default value for non required message value property")
    void ShouldHaveDefaultValueForNonRequiredMessageValueProperty() {
        Map<String, String> props = new HashMap<>();
        FecConfig config = new FecConfig(props);
        String defaultValue = "#{Name.full_name}";
        String propertyValue = config.getString(FecConfig.MESSAGE_VALUE);
        Assertions.assertEquals(defaultValue, propertyValue);
    }

    @Test
    @DisplayName("Should have default value for non required topic name property")
    void ShouldHaveDefaultValueForNonRequiredProperty() {
        Map<String, String> props = new HashMap<>();
        FecConfig config = new FecConfig(props);
        String defaultValue = "topicName";
        String propertyValue = config.getString(FecConfig.TOPIC_NAME);
        Assertions.assertEquals(defaultValue, propertyValue);
    }

    @Test
    @DisplayName("Should define the Value for non required message value property")
    void shouldDefineTheValueForNonRequiredMessageValueProperty() {
        Map<String, String> props = new HashMap<>();
        props.put(FecConfig.MESSAGE_VALUE, "#{PhoneNumber.phoneNumber}");
        FecConfig config = new FecConfig(props);
        Assertions.assertEquals("#{PhoneNumber.phoneNumber}", config.getString(FecConfig.MESSAGE_VALUE));
    }

    @Test
    @DisplayName("Should define the Value for non required topic name property")
    void shouldDefineTheValueForNonRequiredTopicNameProperty() {
        Map<String, String> props = new HashMap<>();
        props.put(FecConfig.TOPIC_NAME, "topic");
        FecConfig config = new FecConfig(props);
        Assertions.assertEquals("topic", config.getString(FecConfig.TOPIC_NAME));
    }

}
