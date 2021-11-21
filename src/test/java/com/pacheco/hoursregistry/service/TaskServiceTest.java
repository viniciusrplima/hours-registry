package com.pacheco.hoursregistry.service;

import com.pacheco.hoursregistry.dto.TaskDTO;
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
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
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
    public UserService userService;

    @Mock
    public TaskRepository taskRepository;

    @Mock
    public AuthorizationUtil authorizationUtil;

    public User user;

    public Task task;

    public TaskDTO taskDTO;

    @BeforeEach
    public void setUp() {
        this.user = new User("jimmy", "jimmy123", null, List.of(new Role(RoleTypes.USER)));
        this.task = new Task("task nova", this.user);
        this.taskDTO = new TaskDTO(false, "task dto");

        Mockito.when(authorizationUtil.currentUsername()).thenReturn("vinicius");
    }

    @Test
    public void testFindTasks() {
        Mockito.when(taskRepository.findTasksByUserUsername(Mockito.anyString())).thenReturn(List.of(new Task("aaa", user)));

        List<Task> tasks = taskService.findAllTasks();

        Assert.assertEquals(1, tasks.size());
        Assert.assertEquals("aaa", tasks.get(0).getResume());
        Assert.assertEquals(user, tasks.get(0).getUser());
    }

    @Test
    public void testFindTask() {
        Mockito.when(taskRepository.findTaskByUserUsernameAndId(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(Optional.of(this.task));

        Task task = taskService.consultTask(1L);

        Assert.assertEquals(this.task, task);
    }

    @Test
    public void testFindTaskNotExists() {
        Mockito.when(taskRepository.findTaskByUserUsernameAndId(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Assert.assertThrows(NoEntityFoundException.class, () -> taskService.consultTask(1L));
    }

    @Test
    public void testRegisterTask() {
        Mockito.when(userService.find(Mockito.anyString())).thenReturn(user);
        Mockito.when(taskRepository.save(Mockito.any())).then(new Answer<Task>() {
            @Override
            public Task answer(InvocationOnMock invocationOnMock) throws Throwable {
                return (Task) invocationOnMock.getArguments()[0];
            }
        });

        String taskResume = "my task resume";
        Task task = taskService.registerTask(taskResume);

        Assert.assertEquals(taskResume, task.getResume());
        Assert.assertFalse(task.getDone());
        Assert.assertNull(task.getEfforts());
        Assert.assertEquals(user, task.getUser());
    }

    @Test
    public void testRegisterTaskUserNotExists() {
        Mockito.when(userService.find(Mockito.anyString())).thenThrow(NoEntityFoundException.class);

        Assert.assertThrows(NoEntityFoundException.class, () -> taskService.registerTask(""));
    }

    @Test
    public void testUpdateTask() {
        Mockito.when(taskRepository.findTaskByUserUsernameAndId(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(Optional.of(this.task));
        Mockito.when(taskRepository.save(Mockito.any())).then(new Answer<Task>() {
            @Override
            public Task answer(InvocationOnMock invocationOnMock) throws Throwable {
                return (Task) invocationOnMock.getArguments()[0];
            }
        });

        Task myTask = taskService.updateTask(1L, this.taskDTO);

        Assert.assertEquals(this.taskDTO.getResume(), myTask.getResume());
        Assert.assertEquals(this.taskDTO.getDone(), myTask.getDone());
    }

    @Test
    public void testUpdateTaskNotExists() {
        Mockito.when(taskRepository.findTaskByUserUsernameAndId(Mockito.anyString(), Mockito.anyLong()))
                .thenReturn(Optional.empty());

        Assert.assertThrows(NoEntityFoundException.class, () -> taskService.updateTask(1L, this.taskDTO));
    }
}
