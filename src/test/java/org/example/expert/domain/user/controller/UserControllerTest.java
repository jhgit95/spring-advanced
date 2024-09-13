package org.example.expert.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.expert.SetupObject;
import org.example.expert.config.AuthUserArgumentResolver;
import org.example.expert.config.JwtFilter;
import org.example.expert.config.MockTestFilter;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;
import org.example.expert.domain.user.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {UserController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = JwtFilter.class
                )
        }
)
class UserControllerTest {
    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthUserArgumentResolver authUserArgumentResolver;

    @Mock
    private UserRole userRole;

    @Autowired
    private WebApplicationContext wac; //

    private Principal mockPrincipal; //

    @Test
    @DisplayName("getUser")
    public void getUser_성공() throws Exception {
        // given
        long userId = 1L;
        given(userService.getUser(userId)).willReturn(new UserResponse(userId, "email"));

        // when
        ResultActions resultActions = mockMvc.perform(get("/users/{userId}", userId));

        // then
        resultActions.andExpect(status().isOk());
    }


    @BeforeEach
    public void setUp() {
        // mockMvc에 사용할 Filter를 MockTestFilter로 지정
        mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new MockTestFilter()) // 중요한 부분
                .build();
    }

    @Test
    @DisplayName("비밀번호 변경 - 200 OK")
    public void changePassword_성공() throws Exception {
        // given
        User user = SetupObject.testUser;
        ReflectionTestUtils.setField(user, "id", 1L);
        UserChangePasswordRequest userChangePasswordRequest = SetupObject.userChangePasswordRequest;

        // 객체를 json 형태로 변환해주는 코드
        String passwordDtoJson = new ObjectMapper().writeValueAsString(userChangePasswordRequest); // 이게 스트링을 json 만들어주는거임

        // when - then
        mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(passwordDtoJson)  // 요청 본문 JSON 데이터
                        .requestAttr("userId", 1L)
                        .requestAttr("email", "test@test.test")
                        .requestAttr("userRole", "USER")) // 어트리뷰터 넣는 법
                .andExpect(status().isOk())
                .andDo(print()); // 디테일하게 콘솔창에 뜸
    }


}

