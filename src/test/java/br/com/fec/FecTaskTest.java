package br.com.fec;

import org.apache.kafka.connect.source.SourceRecord;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static br.com.fec.FecConfig.MESSAGE_VALUE;
import static br.com.fec.FecConfig.TOPIC_NAME;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class FecTaskTest {

    @Test
    @DisplayName("should matches task version")
    void shouldMatchesTaskVersion() {
        String version = PropertiesUtil.getConnectorVersion();
        assertEquals(version, new FecTask().version());
    }

    @Test
    @DisplayName("should matches the number of records")
    void shouldMatchesTheNumberOfRecords() {
        Map<String, String> connectorProps = new HashMap<>();
        connectorProps.put(MESSAGE_VALUE, "nome");
        connectorProps.put(TOPIC_NAME, "topic");
        Map<String, String> taskProps = getTaskProps(connectorProps);
        FecTask task = new FecTask();

        assertDoesNotThrow(() -> {
            task.start(taskProps);
            List<SourceRecord> records = task.poll();
            assertEquals(1, records.size());
        });
    }

    @Test
    @DisplayName("should generates a key from the record with value")
    void shouldGeneratesAKeyFromTheRecordWithValue() throws InterruptedException {
        Map<String, String> connectorProps = new HashMap<>();
        connectorProps.put(MESSAGE_VALUE, "#{Name.first_name}");

        Map<String, String> taskProps = getTaskProps(connectorProps);
        FecTask task = new FecTask();
        task.start(taskProps);
        List<SourceRecord> sourceRecords = task.poll();
        int size = sourceRecords.size();
        String key = new String((byte[])sourceRecords.get(0).key());

        assertEquals(1, size);
        assertEquals(true, key.matches("..id.:.+"));
    }

    @Test
    @DisplayName("should generates a value from the record with value")
    void shouldGeneratesAValueFromTheRecordWithValue() throws InterruptedException {
        Map<String, String> connectorProps = new HashMap<>();
        connectorProps.put(MESSAGE_VALUE, "#{Name.first_name}");

        Map<String, String> taskProps = getTaskProps(connectorProps);
        FecTask task = new FecTask();
        task.start(taskProps);
        List<SourceRecord> sourceRecords = task.poll();
        int size = sourceRecords.size();
        String value = new String((byte[])sourceRecords.get(0).value());

        assertEquals(1, size);
        assertEquals(true, value.matches("..message.:.+"));
    }

    private Map<String, String> getTaskProps(Map<String, String> connectorProps) {
        FecConnector connector = new FecConnector();
        connector.start(connectorProps);
        List<Map<String, String>> taskConfigs = connector.taskConfigs(1);
        return taskConfigs.get(0);
    }

}
