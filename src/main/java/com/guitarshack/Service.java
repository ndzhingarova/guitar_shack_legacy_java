package com.guitarshack;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Service<T> {
    private final  String baseUrl;
    private final Class<T> responseType;

    public Service(String baseUrl, Class<T> responseType) {
        this.baseUrl = baseUrl;
        this.responseType = responseType;
    }

    T getObject(String paramString) {
        HttpRequest request = HttpRequest
                .newBuilder(URI.create(baseUrl + paramString))
                .build();
        String result = "";
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpResponse<String> response = null;
        try {
            response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            result = response.body();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return new Gson().fromJson(result, responseType);
    }
}