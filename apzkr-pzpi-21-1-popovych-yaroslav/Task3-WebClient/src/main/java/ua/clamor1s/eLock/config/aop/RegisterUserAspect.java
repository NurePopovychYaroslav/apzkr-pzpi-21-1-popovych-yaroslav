package ua.clamor1s.eLock.config.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ua.clamor1s.eLock.dto.request.UserRequest;
import ua.clamor1s.eLock.service.UserService;
import ua.clamor1s.eLock.utils.AuthUtils;

import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class RegisterUserAspect {

    private final UserService userService;
    private final AuthUtils authUtils;

    @Pointcut("@annotation(ua.clamor1s.eLock.utils.annotations.Register)")
    public void userRegisterAuditPointcut() {
    }

    @Before("userRegisterAuditPointcut()")
    @Transactional
    public void registerUserIfNotExist() {
        Optional<DefaultOidcUser> user = authUtils.getAuthUser();
        user.ifPresent(this::createUserIfNotExist);
    }

    private void createUserIfNotExist(DefaultOidcUser user) {
        String email = user.getEmail();
        String firstName = user.getGivenName();
        String lastName = user.getFamilyName();
        UserRequest userRequest = new UserRequest(email, firstName, lastName);
        userService.createUserIfNotExist(userRequest);
    }

}
