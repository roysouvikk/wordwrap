package transport.input;

import com.test.wrapper.InputObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import transport.output.OutputFactory;
import utils.Config;
import utils.ConfigException;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.*;

public class KafkaInputAgent implements TransPortInputAgent {

    public static final Logger logger = LoggerFactory.getLogger(KafkaInputAgent.class);
    private Consumer<String,String> consumer;
    private ConsumerRecords<String, String> records;
    private Iterator<ConsumerRecord<String, String>> currIndex;
    private int remaining;
    private List<ConsumerRecord<String, String>> buffer;

    @Override
    public void configure(Config config) throws ConfigException {

        final Properties props = new Properties();
        String CONFIG_BOOTSTRAP_SERVERS = config.getProperty("BOOTSTRAP_SERVERS");
        String CONFIG_APPLICATION_ID = config.getProperty("APPLICATION_ID");
        String topic = config.getProperty("KAFKA_INPUT_AGENT_TOPIC");


        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, CONFIG_BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, CONFIG_APPLICATION_ID);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

        consumer.subscribe(Arrays.asList(topic));
    }

    @Override
    public InputObject nextInput(){

        if(consumer == null){
            return null;
        }
        if(remaining  == 0){
            if(buffer !=null){
                consumer.commitSync();
            }
            buffer = null;
        }
        ConsumerRecords<String, String> records = consumer.poll(1000);
        if(records != null){
            buffer = new ArrayList<>(records.count());
            for (ConsumerRecord<String, String> record : records) {
                buffer.add(record);
            }
            remaining = records.count();
        }
        InputObject result = new InputObject(buffer.get(buffer.size() - remaining).value(), null);
        remaining--;
        return result;
    }

    @Override
    public void close(){

        consumer.close();
        consumer = null;
    }

}
