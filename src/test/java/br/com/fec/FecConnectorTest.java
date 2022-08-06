package br.com.fec;

import org.apache.kafka.connect.connector.Task;
import org.apache.kafka.connect.errors.ConnectException;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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
