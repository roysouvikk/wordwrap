package utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    public static final Logger logger = LoggerFactory.getLogger(Config.class);

    private String propertiesFile;
    private Properties prop;

    public Config(String propertiesFile) throws IOException {
        this.propertiesFile = propertiesFile;
        ClassLoader classLoader = Config.class.getClassLoader();
        InputStream propConfigStream = classLoader.getResourceAsStream(propertiesFile);
        if (propConfigStream != null) {
            logger.warn("Reading config from properties file");
            prop = new Properties();
            prop.load(propConfigStream);
            propConfigStream.close();
        } else {
            logger.info("Reading config from environment");
        }
    }

    public String getProperty(String key) {
        String value;
        value = System.getenv(key);
        if (value == null) {
            if (prop != null) {
                value = prop.getProperty(key, "").trim();
                if(value.length()==0) {
                    value = null;
                }
                logger.info("{} : {} ({})", key, value, propertiesFile);
            }
        } else {
            value = value.trim();
            if(value.length()==0) {
                value = null;
            }
            logger.info("{} : {} (Environment)", key, value);
        }
        return value;
    }
}
