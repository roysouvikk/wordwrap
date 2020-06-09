package transport.input;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import transport.output.OutputFactory;
import utils.Config;
import utils.ConfigConstants;
import utils.ConfigException;

import java.lang.reflect.Constructor;

public class InputFactory {

    public static final Logger logger = LoggerFactory.getLogger(OutputFactory.class);
    private static InputFactory instance = null;

    private InputFactory(){

    }

    public static InputFactory getInstance(){

        if(instance == null){
            synchronized(InputFactory.class){
                if(instance == null){
                    instance = new InputFactory();
                }
            }
        }
        return instance;
    }

    public  TransPortInputAgent getInputAgent(Config config) throws ConfigException {

        String inputAgentName = config.getProperty(ConfigConstants.INPUT_AGENT);
        String name = inputAgentName.trim();

        try {

            Class cls = Class.forName(name);
            Constructor<?> cons = cls.getConstructor();
            TransPortInputAgent agent = (TransPortInputAgent)cons.newInstance();
            agent.configure(config);
            return agent;
        } catch (Exception e) {
            logger.error("{} getInputAgent error: ", e.getCause());
            throw new ConfigException(e.getMessage());
        }
    }
}
