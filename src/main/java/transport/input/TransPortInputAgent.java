package transport.input;

import com.test.wrapper.InputObject;
import utils.Config;
import utils.ConfigException;

public interface TransPortInputAgent {

    void configure(Config config) throws ConfigException;

    InputObject nextInput();

    void close();
}
