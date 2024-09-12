package org.example.expert.config;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.example.expert.domain.user.entity.User;

import java.io.IOException;


@Slf4j
public class CustomFilter implements Filter {
    /**
     * Mock Test용 우회 필터
     * @param servletRequest Mock principal이 담긴 Request 객체
     * @param servletResponse Attribute가 담길 Responce객체
     * @param filterChain 다음 Filter Chain
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        try {


//            insertRequestUser


            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(e.getMessage());
        }
    }

    /**
     * HttpServlerRequest에 user정보 삽입하는 메서드
     * @param user 삽입할 유저 정보
     * @param request request 객체
     */
    private void insertRequestUser(User user, HttpServletRequest request) {
        // @AutoUser 에너테이션을 위해서 해당 Attribute를 넣어준다.
        request.setAttribute("userId", user.getId());
        request.setAttribute("email", user.getEmail());
        request.setAttribute("userRole", user.getUserRole().name());
    }

}
