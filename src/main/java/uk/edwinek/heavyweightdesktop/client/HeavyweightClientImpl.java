package uk.edwinek.heavyweightdesktop.client;

import org.joda.time.DateTime;
import uk.edwinek.heavyweightdesktop.model.HeavyweightResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import uk.edwinek.heavyweightdesktop.model.Reign;

import java.util.*;

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

        try {
            ResponseEntity<HeavyweightResponse> forEntity= restTemplate.getForEntity(buildUrl(date), HeavyweightResponse.class, parameters);
            response = forEntity.getBody();

            if (response.getReigns() == null) {
                List<Reign> reignList = new ArrayList<>();
                reignList.add(new Reign.Builder().withReignBegan(new DateTime(date).toDate()).withChampion("Vacant").build());
                response.setReigns(reignList);
            }

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
