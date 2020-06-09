package transport.output;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Config;
import utils.ConfigException;


public class StdoutAgent implements TransportOutputAgent{

    public static final Logger logger = LoggerFactory.getLogger(StdoutAgent.class);

    public void configure(Config config) throws ConfigException {
        // No-op
    }

    public void send(String outStr){
        System.out.print(outStr);
        System.out.println("");
    }

    public void close(){

    }
}
