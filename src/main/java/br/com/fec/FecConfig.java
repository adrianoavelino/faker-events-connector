package br.com.fec;

import org.apache.kafka.common.config.AbstractConfig;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigDef.Type;
import org.apache.kafka.common.config.ConfigDef.Importance;

import java.util.Map;

public class FecConfig extends AbstractConfig {
    public static final String TOPIC_NAME = "topic.name";
    private static final String TOPIC_NAME_DOC = "This is the name of the topic ";
    private static final String TOPIC_NAME_DEFAULT = "topicName";

    public static final String MESSAGE_VALUE = "message.value";
    private static final String MESSAGE_VALUE_DOC = "This is the message";
    private static final String MESSAGE_VALUE_DEFAULT = "#{Name.full_name}";

    public static final String MONITOR_THREAD_TIMEOUT_CONFIG = "monitor.thread.timeout";
    private static final String MONITOR_THREAD_TIMEOUT_DOC = "Timeout used by the monitoring thread";
    private static final int MONITOR_THREAD_TIMEOUT_DEFAULT = 10000;

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
        configDef.define(TOPIC_NAME,
                        Type.STRING,
                        TOPIC_NAME_DEFAULT,
                        Importance.LOW,
                        TOPIC_NAME_DOC)
                .define(MESSAGE_VALUE,
                        Type.STRING,
                        MESSAGE_VALUE_DEFAULT,
                        Importance.HIGH,
                        MESSAGE_VALUE_DOC)
                .define(MONITOR_THREAD_TIMEOUT_CONFIG,
                        Type.INT,
                        MONITOR_THREAD_TIMEOUT_DEFAULT,
                        Importance.LOW,
                        MONITOR_THREAD_TIMEOUT_DOC);
    }
}
