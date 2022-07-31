package br.com.fec;

import org.apache.kafka.common.config.Config;
import org.apache.kafka.common.config.ConfigDef;
import org.apache.kafka.common.config.ConfigValue;
import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;
import org.apache.kafka.connect.source.SourceConnector;
import org.apache.kafka.connect.util.ConnectorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static br.com.fec.FecConfig.*;
import java.util.*;

public class FecConnect extends SourceConnector {
    private final Logger log = LoggerFactory.getLogger(FecConnect.class);

    private Map<String, String> originalProps;
    private FecConfig config;
    private SourceMonitorThread sourceMonitorThread;

    @Override
    public String version() {
        return PropertiesUtil.getConnectorVersion();
    }

    @Override
    public ConfigDef config() {
        return CONFIG_DEF;
    }

    @Override
    public Class<? extends Task> taskClass() {
        return FecTask.class;
    }

    @Override
    public Config validate(Map<String, String> connectorConfigs) {
        Config config = super.validate(connectorConfigs);
        List<ConfigValue> configValues = config.configValues();
        if (validaCampoNulo(configValues)) {
            throw new ConnectException(String.format("%s.config() must return a ConfigDef that is not null.", this.getClass().getName()));
        }
        return config;
    }

    private boolean validaCampoNulo(List<ConfigValue> configValues) {
        return configValues.stream()
                .filter(v -> v.value() == null)
                .findAny()
                .isPresent();
    }

    @Override
    public void start(Map<String, String> originalProps) {
        this.originalProps = originalProps;
        config = new FecConfig(originalProps);
        String message = config.getString(MESSAGE_VALUE);
        int monitorThreadTimeout = config.getInt(MONITOR_THREAD_TIMEOUT_CONFIG);
        sourceMonitorThread = new SourceMonitorThread(context, message, monitorThreadTimeout);
        sourceMonitorThread.start();
    }

    @Override
    public List<Map<String, String>> taskConfigs(int maxTasks) {
        List<Map<String, String>> taskConfigs = new ArrayList<>();
        List<String> partitions = sourceMonitorThread.getCurrentSources();
        if (partitions.isEmpty()) {
            taskConfigs = Collections.emptyList();
            log.warn("No tasks created because there is zero to work on");
        } else {
            int numTasks = Math.min(partitions.size(), maxTasks);
            List<List<String>> partitionSources = ConnectorUtils.groupPartitions(partitions, numTasks);
            for (List<String> source : partitionSources) {
                Map<String, String> taskConfig = new HashMap<>(originalProps);
                taskConfig.put("sources", String.join(",", source));
                taskConfigs.add(taskConfig);
            }
        }
        return taskConfigs;
    }

    @Override
    public void stop() {
        sourceMonitorThread.shutdown();
    }
}
