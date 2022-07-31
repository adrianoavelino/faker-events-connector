package br.com.fec;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.kafka.connect.data.Schema;
import org.apache.kafka.connect.source.SourceRecord;
import org.apache.kafka.connect.source.SourceTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

import static br.com.fec.FecConfig.*;

public class FecTask extends SourceTask {

    private static Logger log = LoggerFactory.getLogger(FecTask.class);

    private FecConfig config;
    private int monitorThreadTimeout;
    private List<String> sources;
    private Map<String, String> props = new HashMap<>();
    private String topicName;
    private String message;

    @Override
    public String version() {
        return PropertiesUtil.getConnectorVersion();
    }

    @Override
    public void start(Map<String, String> properties) {
        config = new FecConfig(properties);
        monitorThreadTimeout = config.getInt(MONITOR_THREAD_TIMEOUT_CONFIG);
        this.props = config.originalsStrings();
        this.topicName = config.getString(TOPIC_NAME);
        this.message = config.getString(MESSAGE_VALUE);
    }

    @Override
    public List<SourceRecord> poll() throws InterruptedException {
        Thread.sleep(monitorThreadTimeout / 2);
        List<SourceRecord> records = new ArrayList<>();
        Faker faker = new Faker(new Locale("pt-BR"));
        Gson gson = new GsonBuilder().create();

        HashMap<String, String> mapPayloadValue = new HashMap<>();
        HashMap<String, Long> mapPayloadKey = new HashMap<>();

        long randomId = faker.number().randomNumber();
        mapPayloadKey.put("id", randomId);
        mapPayloadValue.put("message", faker.expression(message));

        SourceRecord sourceRecord = new SourceRecord(Collections.singletonMap("source", null),
                Collections.singletonMap("offset", 0),
                topicName,
                null,
                Schema.BYTES_SCHEMA,
                gson.toJson(mapPayloadKey).getBytes(),
                Schema.BYTES_SCHEMA,
                gson.toJson(mapPayloadValue).getBytes()
                );

        records.add(sourceRecord);

        log.info("Message with id {} sent", randomId);
        return records;
    }

    @Override
    public void stop() {
    }

}
