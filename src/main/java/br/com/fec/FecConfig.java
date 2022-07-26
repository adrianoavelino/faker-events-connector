package br.com.fec;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.ConfigDef.Importance;

import java.util.Map;

public class FecConfig extends AbstractConfig {
    public static final String MESSAGE_VALUE = "message.value";
    private static final String MESSAGE_VALUE_DOC = "This is the message";
    private static final String MESSAGE_VALUE_DEFAULT = "Hello World!";
    public static final ConfigDef CONFIG_DEF = createConfigDef();

    public FecConfig(final Map<?, ?> originalProps) {
        super(CONFIG_DEF, originalProps);
    }

    private static ConfigDef createConfigDef() {
        ConfigDef configDef = new ConfigDef();
        addParams(configDef);
        return configDef;
    }

    private static void addParams(final ConfigDef configDef) {
        configDef.define(MESSAGE_VALUE,
                Type.STRING,
                MESSAGE_VALUE_DEFAULT,
                Importance.HIGH,
                MESSAGE_VALUE_DOC);
    }
}
