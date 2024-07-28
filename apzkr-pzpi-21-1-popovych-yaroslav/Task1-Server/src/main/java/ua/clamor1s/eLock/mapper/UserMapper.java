package ua.clamor1s.eLock.mapper;

import org.mapstruct.BeanMapping;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;
import ua.clamor1s.eLock.dto.request.UserRequest;
import ua.clamor1s.eLock.dto.response.UserResponse;
import ua.clamor1s.eLock.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper extends CreatableUpdatableMapper<User> {

    UserResponse userEntityToUserResponse(User user);

    @BeanMapping(builder = @Builder(disableBuilder = true))
    User userRequestToUserEntity(UserRequest userRequest);
}
