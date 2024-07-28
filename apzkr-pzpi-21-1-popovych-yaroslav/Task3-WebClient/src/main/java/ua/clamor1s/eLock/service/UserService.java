package ua.clamor1s.eLock.service;

import ua.clamor1s.eLock.dto.request.UserRequest;
import ua.clamor1s.eLock.dto.response.UserResponse;
import ua.clamor1s.eLock.entity.User;

public interface UserService {
    User createUser(UserRequest userRequest);

    User createUserIfNotExist(UserRequest userRequest);

    boolean userExistByEmail(String email);

    UserResponse convertUserToUserResponse(User user);
}
