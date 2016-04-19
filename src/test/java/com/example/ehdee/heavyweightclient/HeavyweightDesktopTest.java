package com.example.ehdee.heavyweightclient;

import com.example.ehdee.heavyweightdesktop.client.HeavyweightClient;
import com.example.ehdee.heavyweightdesktop.config.HeavyweightDesktopConfig;
import com.example.ehdee.heavyweightdesktop.model.HeavyweightResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HeavyweightDesktopConfig.class})
public class HeavyweightDesktopTest {

    @Autowired
    private HeavyweightClient heavyweightClientImpl;

    @Test
    public void test() {

        HeavyweightResponse pojo = heavyweightClientImpl.getReignsByDate("2014-01-0100");
        System.out.println(pojo);

    }

}
