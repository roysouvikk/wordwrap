package transport.output;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import transport.input.KafkaInputAgent;
import utils.Config;
import utils.ConfigException;


public class KafkaOutputAgent implements TransportOutputAgent {

    public static final Logger logger = LoggerFactory.getLogger(KafkaInputAgent.class);

    public void configure(Config config) throws ConfigException {

    }

    public void send(String outStr){

    }

    public void close(){

    }

}
