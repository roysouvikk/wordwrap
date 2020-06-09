package transport.output;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import transport.input.InputFactory;

import transport.input.TransPortInputAgent;
import utils.Config;
import utils.ConfigConstants;

import java.lang.reflect.Constructor;

public class OutputFactory {

    public static final Logger logger = LoggerFactory.getLogger(OutputFactory.class);
    private static OutputFactory instance = null;

    private OutputFactory(){

    }

    public static OutputFactory getInstance(){

        if(instance == null){
            synchronized(InputFactory.class){
                if(instance == null){
                    instance = new OutputFactory();
                }
            }
        }
        return instance;
    }

    public TransportOutputAgent getOutputAgent(Config config){

        String outputAgentName = config.getProperty(ConfigConstants.OUTPUT_AGENT);
        String name = outputAgentName.trim();

        try {
            Class cls = Class.forName(name);
            Constructor<?> cons = cls.getConstructor();
            TransportOutputAgent agent = (TransportOutputAgent)cons.newInstance();
            agent.configure(config);
            return agent;
        } catch (Exception e) {
            logger.error("IpnutAgent creation failed with {}", e.getStackTrace());
            return null;
        }
    }
}
