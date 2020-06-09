package transport.input;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import com.test.wrapper.InputObject;
import utils.Config;
import utils.ConfigException;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;


public class RestInputAgent {

    ArrayList<String> requests;
    ArrayList<String> response;

    private class MyHttpHandler implements HttpHandler {

        ArrayList<String> requests;
        ArrayList<String> response;
        String methodName;

        public MyHttpHandler(ArrayList<String> requests, ArrayList<String> response, String methodName){
            this.requests = requests;
            this.response = response;
            this.methodName = methodName;
        }
        @Override

        public void handle(HttpExchange httpExchange) throws IOException {

            String requestParamValue=null;

            if("GET".equals(httpExchange.getRequestMethod())) {

                // Not supported, throw error

            }else if("POST".equals(httpExchange)) {

                requestParamValue = handlePostRequest(httpExchange);

            }


            handleResponse(httpExchange,requestParamValue);

        }

        private String handlePostRequest(HttpExchange httpExchange) {

            if(httpExchange.getRequestMethod().compareTo(this.methodName) !=0){

                try {
                    // Wait Notify
                    while (true) {
                        synchronized (RestInputAgent.class) {
                            if (requests.size() == 0) {
                                String toParse = httpExchange.getRequestBody().toString();
                                requests.add(toParse);
                                break;
                            }
                        }
                        Thread.sleep(10);
                    }
                    String responseStr = null;
                    while (true) {
                        synchronized (RestInputAgent.class) {
                            if (response.size() == 1) {
                                responseStr = response.get(0);
                                response.clear();
                                break;
                            }
                        }
                        Thread.sleep(10);
                        ;
                    }
                } catch (Exception e){

                }

            } else {
                // Throw Error response
            }
          return null;
        }

        private String handleGetRequest(HttpExchange httpExchange) {

            return httpExchange.

            getRequestURI()

                    .toString()

                    .split("\\?")[1]

                    .split("=")[1];

        }


        private void handleResponse(HttpExchange httpExchange, String requestParamValue)  throws  IOException {

            OutputStream outputStream = httpExchange.getResponseBody();

            StringBuilder htmlBuilder = new StringBuilder();



            //outputStream.write(htmlResponse.getBytes());

            outputStream.flush();

            outputStream.close();

        }

    }
    void configure(Config config) throws ConfigException{
        try {
            requests = new ArrayList<>();
            response = new ArrayList<>();

            HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 8001), 0);
            server.createContext("/test", new MyHttpHandler(requests, response));
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) Executors.newFixedThreadPool(1);
            server.setExecutor(threadPoolExecutor);

            server.start();
        } catch(Exception e){

        }
    }

    InputObject nextInput(){


        return null;
    }

    void close(){

    }
}
