package com.pacheco.hoursregistry.service;

import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Role;
import com.pacheco.hoursregistry.model.RoleTypes;
import com.pacheco.hoursregistry.model.Task;
import com.pacheco.hoursregistry.model.User;
import com.pacheco.hoursregistry.repository.TaskRepository;
import com.pacheco.hoursregistry.service.impl.TaskServiceImpl;
import com.pacheco.hoursregistry.util.AuthorizationUtil;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class TaskServiceTest {

    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();

    @InjectMocks
    public TaskServiceImpl taskService;

    @Mock
    public TaskRepository taskRepository;

    @Mock
    public AuthorizationUtil authorizationUtil;

    public User user;

    public Task task;

    @BeforeEach
    public void setUp() {
        this.user = new User("jimmy", "jimmy123", null, List.of(new Role(RoleTypes.USER)));
        this.task = new Task("task nova", this.user);

        Mockito.when(authorizationUtil.currentUsername()).thenReturn("vinicius");
    }

    @Test
    public void findTasks() {
        Mockito.when(taskRepository.findTasksByUserUsername(Mockito.anyString())).thenReturn(List.of(new Task("aaa", user)));

        List<Task> tasks = taskService.findAllTasks();

        Assert.assertEquals(1, tasks.size());
        Assert.assertEquals("aaa", tasks.get(0).getResume());
        Assert.assertEquals(user, tasks.get(0).getUser());
    }

    @Test
    public void findTaskNotExists() {
        Mockito.when(taskRepository.findTaskByUserUsernameAndId(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Assert.assertThrows(NoEntityFoundException.class, () -> taskService.consultTask(1L));
    }
}
