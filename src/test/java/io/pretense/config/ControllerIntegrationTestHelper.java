package io.pretense.config;

import io.pretense.DemoApplicationTests;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(DemoApplicationTests.class)
@WebAppConfiguration
@ActiveProfiles(profiles = "test")
public class ControllerIntegrationTestHelper {
}
