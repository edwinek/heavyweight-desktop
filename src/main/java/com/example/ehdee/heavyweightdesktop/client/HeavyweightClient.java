package com.example.ehdee.heavyweightdesktop.client;

import com.example.ehdee.heavyweightdesktop.model.HeavyweightResponse;

public interface HeavyweightClient {

    HeavyweightResponse getReignsByDate(String date);

    String getHost();

    void setHost(String host);

    String getPort();

    void setPort(String port);
}
