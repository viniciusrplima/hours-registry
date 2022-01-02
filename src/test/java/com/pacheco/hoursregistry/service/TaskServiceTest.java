package com.pacheco.hoursregistry.service;

import com.pacheco.hoursregistry.dto.TaskDTO;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Role;
import com.pacheco.hoursregistry.model.RoleTypes;
import com.pacheco.hoursregistry.model.Task;
import com.pacheco.hoursregistry.model.User;
import com.pacheco.hoursregistry.repository.TaskRepository;
import com.pacheco.hoursregistry.util.AuthorizationUtil;
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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class TaskServiceTest {

    @Rule
    public MockitoRule initRule = MockitoJUnit.rule();

    @InjectMocks
    public TaskService taskService;

    @Mock
    public UserService userService;

    @Mock
    public TaskRepository taskRepository;

    @Mock
    public AuthorizationUtil auth;

    public User user;

    public Task task;

    public TaskDTO taskDTO;

    @BeforeEach
    public void setUp() {
        this.user = new User("jimmy", "jimmy123", null, List.of(new Role(RoleTypes.USER)));
        this.task = new Task("task nova", this.user);
        this.taskDTO = new TaskDTO(false, "task dto");

        when(auth.currentUsername()).thenReturn("vinicius");
    }

    @Test
    public void testFindTask() {
        when(taskRepository.findTaskByUserUsernameAndId(anyString(), anyLong())).thenReturn(Optional.of(this.task));

        Task task = taskService.consultTask(1L);

        assertEquals(this.task, task);
    }

    @Test
    public void testFindTaskNotExists() {
        when(taskRepository.findTaskByUserUsernameAndId(anyString(), anyLong()))
                .thenReturn(Optional.empty());

        assertThrows(NoEntityFoundException.class, () -> taskService.consultTask(1L));
    }

    @Test
    public void testRegisterTask() {
        when(userService.find(anyString())).thenReturn(user);
        when(taskRepository.save(any())).then(new Answer<Task>() {
            @Override
            public Task answer(InvocationOnMock invocationOnMock) throws Throwable {
                return (Task) invocationOnMock.getArguments()[0];
            }
        });

        String taskResume = "my task resume";
        Task task = taskService.registerTask(taskResume);

        assertEquals(taskResume, task.getResume());
        assertFalse(task.getDone());
        assertNull(task.getEfforts());
        assertEquals(user, task.getUser());
    }

    @Test
    public void testRegisterTaskUserNotExists() {
        when(userService.find(anyString())).thenThrow(NoEntityFoundException.class);

        assertThrows(NoEntityFoundException.class, () -> taskService.registerTask(""));
    }

    @Test
    public void testUpdateTask() {
        when(taskRepository.findTaskByUserUsernameAndId(anyString(), anyLong()))
                .thenReturn(Optional.of(this.task));
        when(taskRepository.save(Mockito.any())).then(new Answer<Task>() {
            @Override
            public Task answer(InvocationOnMock invocationOnMock) throws Throwable {
                return (Task) invocationOnMock.getArguments()[0];
            }
        });

        Task myTask = taskService.updateTask(1L, this.taskDTO);

        assertEquals(this.taskDTO.getResume(), myTask.getResume());
        assertEquals(this.taskDTO.getDone(), myTask.getDone());
    }

    @Test
    public void testUpdateTaskNotExists() {
        when(taskRepository.findTaskByUserUsernameAndId(anyString(), anyLong())).thenReturn(Optional.empty());

        assertThrows(NoEntityFoundException.class, () -> taskService.updateTask(1L, this.taskDTO));
    }
}
