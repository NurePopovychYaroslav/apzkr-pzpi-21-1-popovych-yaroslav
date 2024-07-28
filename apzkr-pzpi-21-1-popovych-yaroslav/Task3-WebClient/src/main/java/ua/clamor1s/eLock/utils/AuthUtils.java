package ua.clamor1s.eLock.utils;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Component;
import ua.clamor1s.eLock.entity.User;
import ua.clamor1s.eLock.repository.UserRepository;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthUtils {

    private final UserRepository userRepository;

    public Optional<DefaultOidcUser> getAuthUser() {
        var authenticationToken = SecurityContextHolder.getContext().getAuthentication();
        if (authenticationToken.getPrincipal() instanceof String) {
            return Optional.empty();
        }
        return Optional.of((DefaultOidcUser) authenticationToken.getPrincipal());
    }

    public Optional<User> getCurrentUser() {
        var dou = getAuthUser();
        return dou.stream()
                .map(user -> userRepository.findByEmail(user.getEmail()))
                .flatMap(user -> Optional.ofNullable(user).stream())
                .findFirst();
    }

    public void validateUser(String email) {
        User user = getCurrentUser().orElseThrow(() -> new RuntimeException());
        if (!email.equals(user.getEmail())) {
            throw new RuntimeException();
        }
    }
}
