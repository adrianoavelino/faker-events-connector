package br.com.fec;

import org.apache.kafka.connect.connector.Task;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class FecConnectorTest {
    @Test
    public void connectorVersionShouldMatch() {
        String version = PropertiesUtil.getConnectorVersion();
        assertEquals(version, new FecConnector().version());
    }

    @Test
    public void checkClassTask() {
        Class<? extends Task> taskClass = new FecConnector().taskClass();
        assertEquals(FecTask.class, taskClass);
    }

}
