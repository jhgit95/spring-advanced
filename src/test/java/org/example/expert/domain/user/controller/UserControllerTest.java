package org.example.expert.domain.user.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import org.example.expert.SetupObject;
import org.example.expert.config.*;
import org.example.expert.domain.common.dto.AuthUser;
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
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;

import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

//import static org.assertj.core.api.BDDAssumptions.given;
//import static org.assertj.core.api.BDDAssumptions.given;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    public void getUser_성공() throws Exception{
        // given
        long userId = 1L;
        given(userService.getUser(userId)).willReturn(new UserResponse(userId,"email"));

        // when
        ResultActions resultActions = mockMvc.perform(get("/users/{userId}", userId));
        // then
        resultActions.andExpect(status().isOk());
    }

//    @Test
//    @DisplayName("비밀번호 변경")
//    public void changePassword_성공() throws Exception {
//        // AuthUser 객체의 UserRole 확인
//        System.out.println("UserRole: " + SetupObject.authUserAdmin.getUserRole());
//
//
//        // given
//        long userId = 1L;
//        doNothing().when(userService).changePassword(userId, SetupObject.userChangePasswordRequest);
//         AuthUser authUserAdmin = new AuthUser(1L,"test@test.test", UserRole.ADMIN);
//
//        // when
//        ResultActions resultActions = mockMvc.perform(put("/users")
//                .contentType(MediaType.APPLICATION_JSON)
//                .content("{\"oldPassword\":\"oldPass\",\"newPassword\":\"newPass\"}")  // 요청 본문 JSON 데이터
//                .requestAttr("authUser",SetupObject.authUserAdmin)
//                .requestAttr("userRole", SetupObject.authUserAdmin.getUserRole().name()) // userRole 속성 추가
//
//
//        );
//        // then
//        resultActions.andExpect(status().isOk());
//
//    }

    // 잘 돌아가는 원본
//@Test
//@DisplayName("비밀번호 변경 - 200 OK")
//public void changePassword_성공() throws Exception {
//    // given
////    long userId = 1L;
////    AuthUser authUser = new AuthUser(userId, "test@test.com", UserRole.ADMIN);
//
//    User user = SetupObject.testUser;
//    ReflectionTestUtils.setField(user,"id",1L);
//    UserDetailsImpl userDetails = new UserDetailsImpl(user);
//
//    mockPrincipal = new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
//
//    UserChangePasswordRequest userChangePasswordRequest = SetupObject.userChangePasswordRequest;
//    String zz = new ObjectMapper().writeValueAsString(userChangePasswordRequest); // 이게 스트링을 json 만들어주는거임
//
//
//    // 기븐 웬 덴
//    mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON)
//                    .content(zz)  // 요청 본문 JSON 데이터
//                    .principal(mockPrincipal)) // 여기까지가 httprequest관련내용
//            .andExpect(status().isOk())
//            .andDo(print()); // 디테일하게 콘솔창에 뜸
//
////
//
//
////
////    UserChangePasswordRequest userChangePasswordRequest = new UserChangePasswordRequest("oldPass", "newPass");
////
////    // Mocking the service method
////    doNothing().when(userService).changePassword(userId, userChangePasswordRequest);
////
////    // 직접 컨트롤러 메서드 호출
////    UserController userController = new UserController(userService);
////    userController.changePassword(authUser, userChangePasswordRequest);
////
////    // Verify that the service method was called
////    verify(userService).changePassword(userId, userChangePasswordRequest);
//}

//    @BeforeEach
//    void setUp() {
//        CustomFilter customFilter = new CustomFilter(); // 필터 인스턴스 생성
//        request = new MockHttpServletRequest();
//        response = new MockHttpServletResponse();
//        filterChain = mock(FilterChain.class); // 필터 체인을 모킹
//        customFilter.doFilter(request, response, filterChain);
//    }







//    private MockHttpServletRequest request;
//    private MockHttpServletResponse response;
//    private FilterChain filterChain;



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
        ReflectionTestUtils.setField(user,"id",1L);

        UserChangePasswordRequest userChangePasswordRequest = SetupObject.userChangePasswordRequest;

        // 객체를 json 형태로 변환해주는 코드
        String zz = new ObjectMapper().writeValueAsString(userChangePasswordRequest); // 이게 스트링을 json 만들어주는거임

        // when - then
        mockMvc.perform(put("/users").contentType(MediaType.APPLICATION_JSON)
                        .content(zz)  // 요청 본문 JSON 데이터
//                        .requestAttr("user",user))
                        .requestAttr("userId",1L)
                        .requestAttr("email","test@test.test")
                        .requestAttr("userRole","USER")) // 어트리뷰터 넣는 법
                .andExpect(status().isOk())
                .andDo(print()); // 디테일하게 콘솔창에 뜸

 }




}

