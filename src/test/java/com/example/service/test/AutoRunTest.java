package com.example.service.test;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.example.TestConfig;
import com.example.service.AutoRun;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("Test")
public class AutoRunTest {
 @SpyBean
  AutoRun autoRun;
  @Test
  void whenContextLoads_thenRunnersRun() throws Exception {
    verify(autoRun, times(1)).run(any());
  }
}
