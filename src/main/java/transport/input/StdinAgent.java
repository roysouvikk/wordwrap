package transport.input;

import com.test.wrapper.InputObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Config;
import utils.ConfigException;

import javax.naming.ConfigurationException;
import java.util.Scanner;

public class StdinAgent implements TransPortInputAgent{
    public static final Logger logger = LoggerFactory.getLogger(StdinAgent.class);

    public void configure(Config config) throws ConfigException {
        // no-op
    }

    public InputObject nextInput(){
        Scanner sin = new Scanner(System.in);
        String st =  sin.nextLine();
        return new InputObject(st, null);
    }

    public void close(){
        // no-op
    }
}
