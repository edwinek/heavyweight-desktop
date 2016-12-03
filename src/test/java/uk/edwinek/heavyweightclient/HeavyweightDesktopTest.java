package uk.edwinek.heavyweightclient;

import uk.edwinek.heavyweightdesktop.client.HeavyweightClient;
import uk.edwinek.heavyweightdesktop.config.HeavyweightDesktopConfig;
import uk.edwinek.heavyweightdesktop.model.HeavyweightResponse;
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
