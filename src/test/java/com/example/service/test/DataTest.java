package com.example.service.test;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.example.TestConfig;
import com.example.service.Data;
import java.io.IOException;
import java.net.URISyntaxException;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("Test")

public class DataTest {

  @MockBean
  Data data;

  @Test
  void createData_ShouldCreateData() throws IOException, URISyntaxException {
    data.createData();
    verify(data, times(1)).createData();
  }
}
