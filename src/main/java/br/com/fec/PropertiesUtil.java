package br.com.fec;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.util.Properties;

public final class PropertiesUtil {

    private static final String CONNECTOR_VERSION = "connector.version";
    private static final Logger LOG = LoggerFactory.getLogger(PropertiesUtil.class);
    private static final String PROPERTIES_FILE = "/kafka-connect-sample.properties";
    private static Properties properties;

    static {
        try (InputStream stream = PropertiesUtil.class.getResourceAsStream(
                PROPERTIES_FILE)) {
            properties = new Properties();
            properties.load(stream);
        } catch (Exception ex) {
            LOG.warn("Error while loading properties: ", ex);
        }
    }

    public static String getConnectorVersion() {
        return properties.getProperty(CONNECTOR_VERSION);
    }

    private PropertiesUtil() {
    }

}
