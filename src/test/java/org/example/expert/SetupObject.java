package org.example.expert;

import org.example.expert.config.PasswordEncoder;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.user.dto.request.UserChangePasswordRequest;
import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.enums.UserRole;

public class SetupObject {
    static PasswordEncoder passwordEncoder = new PasswordEncoder();

    private final Long userId=1L;
    public static final AuthUser authUserAdmin = new AuthUser(1L,"test@test.test",UserRole.ADMIN);
    public static AuthUser authUserUser = new AuthUser(1L,"test@test.test",UserRole.USER);
    public static User testUser = new User("test@test.com","testPw", UserRole.ADMIN );
    public static User testUser2 = User.fromAuthUser(authUserAdmin);
    public static UserChangePasswordRequest userChangePasswordRequest = new UserChangePasswordRequest("zz","123!@#qweQWE");
    public static String oldPw = "123!@#qweQWE";
    public static String oldPwEncoding = passwordEncoder.encode(oldPw);
    public static String newPw = "qweQWE123!@#";
    public static String newPwEncoding = passwordEncoder.encode(newPw);


}
