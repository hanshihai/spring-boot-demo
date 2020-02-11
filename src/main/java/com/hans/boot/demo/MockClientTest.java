package com.hans.boot.demo;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.junit.Rule;
import org.junit.Test;
import org.mockserver.client.MockServerClient;
import org.mockserver.junit.MockServerRule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockserver.model.HttpRequest.request;
import static org.mockserver.model.HttpResponse.response;

public class MockClientTest {

    @Rule
    public MockServerRule server = new MockServerRule(this, 1081);

    @Test
    public void test() throws IOException {
        MockServerClient mockClient = new MockServerClient("localhost", 1081);
        String expected = "hello world";

        mockClient.when(
                request()
                        .withPath("/hello")
                        .withMethod("GET")
        ).respond(
                response()
                        .withStatusCode(200)
                        .withBody(expected)
        );

        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet("http://localhost:1081/hello");
        CloseableHttpResponse response = client.execute(httpGet);

        InputStream content = response.getEntity().getContent();
        InputStreamReader inputStreamReader = new InputStreamReader(content);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        String responseText = bufferedReader.readLine();
        assertThat(responseText, equalTo(expected));
    }
}
