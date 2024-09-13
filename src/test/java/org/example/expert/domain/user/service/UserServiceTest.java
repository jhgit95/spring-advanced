package org.example.expert.domain.user.service;

import org.example.expert.SetupObject;
import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;


    UserService userServiceMock = mock(UserService.class);

    @Test
    public void getUser_유저_아이디_없으면_에러_발생(){
        //given
        long userId = 1L;
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        //when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () -> {
             userService.getUser(userId);
        });

        //then
        assertEquals("User not found", exception.getMessage());

    }

    @Test
    public void getUser_동작_성공_테스트(){

        // when
        given(userRepository.findById(anyLong())).willReturn(Optional.of(SetupObject.testUser2));
        UserResponse userDto = userService.getUser(1L);

        // then
        assertNotNull(userDto);

    }


    @Test
    @DisplayName("비밀번호 변경에서 유저를 찾을 수 없는 경우")
    public void changePassword_유저_없음(){
        // given
        doNothing().when(userServiceMock).pwValidCheck(anyString());
        given(userRepository.findById(anyLong())).willReturn(Optional.empty());

        // when
        InvalidRequestException exception = assertThrows(InvalidRequestException.class, () ->
                userService.changePassword(1L,SetupObject.userChangePasswordRequest)
        );

        // then
        assertEquals("User not found", exception.getMessage());
    }

    @Test
    @DisplayName("비밀번호 변경 성공")
    public void changePassword_성공(){
        // given
        User user = mock(User.class);

        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), nullable(String.class)))
                .thenReturn(false)
                .thenReturn(true);

        // when
        userService.changePassword(1L, SetupObject.userChangePasswordRequest);

        // then
        verify(user).changePassword(passwordEncoder.encode(anyString()));
    }




}