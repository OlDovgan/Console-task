package com.example.serviceTest;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import com.example.layer.dao.GroupDao;
import com.example.layer.dao.StudentDao;
import com.example.model.Group;
import com.example.layer.service.GroupService;
import com.example.layer.service.StudentService;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = GroupServiceConfig.class)
@ActiveProfiles("Test")
class GroupServiceTest {

  @Value("${groups}")
  private int groupsTest;
  @MockBean
  GroupDao groupDao;
  @MockBean
  StudentDao studentDao;
  @MockBean
  StudentService studentService;

  @Autowired
  GroupService groupService;

  @Test
  void createData_ShouldAddedSetQuantityGroupsToDb() {
    List<Group> list = new ArrayList<>();
    for (int i = 0; i < groupsTest; i++) {
      list.add(null);
    }
    Mockito.when(groupDao.getAll()).thenReturn(list);
    Assertions.assertEquals(groupsTest, groupService.getAll().size());
  }

  @Test
  void getGroupsByStudentCount_ShouldCallGroupDaoMethodGetGroupsByStudentCount() {
    int num = 3;
    groupService.getGroupsByStudentCount(num);
    verify(groupDao, times(1)).getGroupsByStudentCount(num);
  }

  @Test
  void getAll_ShouldCallGroupDaoMethodGetAll() {
    groupService.getAll();
    verify(groupDao, times(1)).getAll();
  }
}
