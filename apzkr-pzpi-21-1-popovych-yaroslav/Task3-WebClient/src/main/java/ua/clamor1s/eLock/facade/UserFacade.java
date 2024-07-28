package ua.clamor1s.eLock.facade;

import ua.clamor1s.eLock.dto.request.UserRequest;
import ua.clamor1s.eLock.dto.response.UserResponse;

public interface UserFacade {
    UserResponse createUser(UserRequest userRequest);
}
