package com.example.ehdee.heavyweightdesktop.client;

import com.example.ehdee.heavyweightdesktop.model.HeavyweightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.ConnectException;
import java.util.HashMap;
import java.util.Map;

@Component
public class HeavyweightClientImpl implements HeavyweightClient {

    private static final String endpoint = "heavyweight/query/";
    private static final String queryParameter = "date";

    private String host = "127.0.0.1";
    private String port = "8888";

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public HeavyweightResponse getReignsByDate(String date) {
        HeavyweightResponse response;
        Map<String, String> parameters = new HashMap<>();
        parameters.put("date", date);

        ResponseEntity<HeavyweightResponse> forEntity = null;
        try {
            forEntity = restTemplate.getForEntity(buildUrl(date), HeavyweightResponse.class, parameters);
            response = forEntity.getBody();
        } catch (HttpClientErrorException e) {
            response = new HeavyweightResponse.Builder().withError(e.getResponseBodyAsString()).build();
        } catch (RestClientException e) {
            response = new HeavyweightResponse.Builder().withError("Can't connect to \"" + host + ":" + port + "\"").build();
        }
        return response;
    }

    @Override
    public String getHost() {
        return host;
    }

    @Override
    public void setHost(String host) {
        this.host = host;
    }

    @Override
    public String getPort() {
        return port;
    }

    @Override
    public void setPort(String port) {
        this.port = port;
    }

    private String buildUrl(String date) {
        return UriComponentsBuilder.newInstance()
                .scheme("http")
                .host(host)
                .port(port)
                .path(endpoint)
                .queryParam(queryParameter, date)
                .build().toUriString();
    }
}
