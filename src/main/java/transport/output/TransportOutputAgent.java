package transport.output;

import utils.Config;
import utils.ConfigException;

public interface TransportOutputAgent {

    public void configure(Config config) throws ConfigException;

    public void send(String outStr);

    public void close();
}
