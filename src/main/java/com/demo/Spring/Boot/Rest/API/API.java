package com.demo.Spring.Boot.Rest.API;

//import com.sun.net.httpserver.HttpServer;

//import java.io.IOException;
//import java.io.OutputStream;
//import java.net.InetSocketAddress;
import static spark.Spark.*;


public class API {

//        public static void main(String[] args) throws IOException {

//            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
//            server.createContext("/api/greeting", (exchange -> {
//
//                if ("GET".equals(exchange.getRequestMethod())) {
//                    String responseText = "Hello World! from our framework-less REST API\n";
//                    exchange.sendResponseHeaders(200, responseText.getBytes().length);
//                    OutputStream output = exchange.getResponseBody();
//                    output.write(responseText.getBytes());
//                    output.flush();
//                } else {
//                    exchange.sendResponseHeaders(405, -1);// 405 Method Not Allowed
//                }
//                exchange.close();
//            }));
//
//
//            server.setExecutor(null); // creates a default executor
//            server.start();
            public static void main(String[] args) {
                get("/greeting", (req, res) -> "Hello World! from our Spark REST API");
                //＄ curl http://localhost:4567/greeting
                //
            }

    }

