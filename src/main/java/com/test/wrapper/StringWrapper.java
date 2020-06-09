package com.test.wrapper;

import transport.input.InputFactory;
import transport.input.TransPortInputAgent;
import transport.output.OutputFactory;
import transport.output.TransportOutputAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utils.Config;
import utils.ConfigConstants;
import utils.ConfigException;

import java.io.IOException;

import static utils.ConfigConstants.APPLICATION_ID;

public class StringWrapper {

    public static final Logger logger = LoggerFactory.getLogger(StringWrapper.class);
    Config config;
    protected TransportOutputAgent outChannel;
    TransPortInputAgent inChannel;
    protected int maxLength = 0;

    public void configure(Config config) throws ConfigException {

        this.config = config;
        this.maxLength = Integer.parseInt(config.getProperty(ConfigConstants.MAX_STRING_WRAPPER_LIMIT));
        InputFactory inputFactory = InputFactory.getInstance();
        inChannel = inputFactory.getInputAgent(config);
        OutputFactory outputFactory = OutputFactory.getInstance();
        outChannel = outputFactory.getOutputAgent(config);
    }

    public static void main(String[] args)  {
        try {

            StringWrapper appInstance = new StringWrapper();
            appInstance.configure(getConfig());
            System.out.println(appInstance.config.getProperty(APPLICATION_ID));
            logger.info(appInstance.config.getProperty(APPLICATION_ID));
            appInstance.runAgent();

        } catch(Exception e){
            logger.error("Exception received: {}", e.getCause());
        }
    }


    public static String getPropertiesFile(){
        return "test.properties";
    }

    public static Config getConfig() throws IOException {
        return new Config(getPropertiesFile());
    }

    void runAgent() throws InterruptedException{

        try {
            while (true) {
                InputObject toWrap = this.inChannel.nextInput();
                if (toWrap != null) {
                    String wrappedString = wrapAString(toWrap.serializedObject);
                    this.outChannel.send(wrappedString);
                } else {
                    //TODO : Error reporting if necessary
                    // To break busy wait
                    Thread.sleep(100);
                }
            }
        } catch (Exception e){
            logger.error(e.getMessage());
            throw e;
        }
    }

    public String wrapAString(String toWrap){
        if(toWrap == null || toWrap.length() <= this.maxLength)
            return toWrap;

        String[] words = toWrap.split("\\s");
        int currIndex = 0;
        int currLength = 0;
        StringBuilder out = new StringBuilder();
        do {
            if(currLength + words[currIndex].length() > (this.maxLength)){
                out.append(System.lineSeparator() );
                currLength = 0;
            }

            out.append(words[currIndex]);
            currLength += words[currIndex].length();
            if(currIndex != words.length -1) {
                out.append(" ");
                currLength++;
            }

            currIndex++;
        } while(currIndex < words.length);

        return out.toString();
    }
}
