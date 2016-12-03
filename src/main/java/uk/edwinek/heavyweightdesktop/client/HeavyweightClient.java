package uk.edwinek.heavyweightdesktop.client;

import uk.edwinek.heavyweightdesktop.model.HeavyweightResponse;

public interface HeavyweightClient {

    HeavyweightResponse getReignsByDate(String date);

    String getHost();

    void setHost(String host);

    String getPort();

    void setPort(String port);
}
