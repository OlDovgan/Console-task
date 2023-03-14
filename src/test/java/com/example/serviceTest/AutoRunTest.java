package com.example.serviceTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.example.menu.AppMenu;
import com.example.service.Data;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = AutoRunConfig.class)
@ActiveProfiles("Test")
class AutoRunTest {
  @MockBean
  Data data;
  @MockBean
  AppMenu menu;

  @Test
  void whenContextLoads_thenRunnersRun() throws Exception {
    verify(data, times(1)).createData();
    verify(menu, times(1)).run();
  }
}
