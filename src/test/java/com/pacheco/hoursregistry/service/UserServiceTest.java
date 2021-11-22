package com.pacheco.hoursregistry.service;

import com.pacheco.hoursregistry.dto.UserDTO;
import com.pacheco.hoursregistry.exception.DuplicityEntityException;
import com.pacheco.hoursregistry.exception.NoEntityFoundException;
import com.pacheco.hoursregistry.model.Role;
import com.pacheco.hoursregistry.model.RoleTypes;
import com.pacheco.hoursregistry.model.User;
import com.pacheco.hoursregistry.repository.RoleRepository;
import com.pacheco.hoursregistry.repository.UserRepository;
import com.pacheco.hoursregistry.service.impl.UserServiceImpl;
import org.junit.Rule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UserServiceTest {

    @Rule
    private MockitoRule initRule = MockitoJUnit.rule();

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    public User user;

    public UserDTO userDTO;

    @BeforeEach
    public void setUp() {
        this.user = new User("jimmy", "jimmy123", null, List.of(new Role(RoleTypes.USER)));
        this.userDTO = new UserDTO("jimmy", "jimmy123", null);
    }

    @Test
    public void testFind() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(this.user));

        User user = userService.find("");

        assertEquals(this.user, user);
    }

    @Test
    public void testFindNotExists() {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.empty());

        assertThrows(NoEntityFoundException.class, () -> userService.find(""));
    }

    @Test
    public void testRegister() {
        String encodedPassword = passwordEncoder.encode(userDTO.getPassword());

        when(roleRepository.findByNameIn(any())).thenReturn(List.of());
        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword);
        when(userRepository.save(any())).then(new Answer<User>() {
            @Override
            public User answer(InvocationOnMock invocationOnMock) throws Throwable {
                return (User) invocationOnMock.getArguments()[0];
            }
        });

        User user = userService.register(this.userDTO, List.of());

        assertEquals(userDTO.getUsername(), user.getUsername());
        assertEquals(encodedPassword, user.getPassword());
        assertEquals(userDTO.getGithubToken(), user.getGithubToken());
    }

    @Test
    public void testRegisterDuplicityEntity() {
        when(roleRepository.findByNameIn(any())).thenReturn(List.of());
        when(passwordEncoder.encode(anyString())).thenReturn("");
        when(userRepository.save(any())).thenThrow(DataIntegrityViolationException.class);

        assertThrows(DuplicityEntityException.class, () -> userService.register(this.userDTO, List.of()));
    }

}
