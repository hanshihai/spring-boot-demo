package com.hans.boot.demo;

import org.mockserver.integration.ClientAndServer;

import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;
import static org.mockserver.model.Parameter.param;

public class MockServerDemo {
    public static void main(String[] args) throws Exception {
        ClientAndServer server = new ClientAndServer(1080);
        String result = Util.load("sample.json");
        server.when(
                request()
                        .withMethod("GET")
                        .withPath("/test")
                        .withQueryStringParameters(
                                param("p", "1")
                        )
        ).respond(
                response()
                        .withBody("this is test 1.")
        );

        server.when(
                request()
                        .withMethod("GET")
                        .withPath("/sample")
        ).respond(
                response()
                        .withBody(result)
        );
    }
}
