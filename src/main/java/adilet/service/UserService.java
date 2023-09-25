package adilet.service;

import adilet.dto.SimpleResponse;
import adilet.dto.request.UserRequest;
import adilet.dto.response.UserResponse;

import java.util.List;

public interface UserService {

    SimpleResponse saveUserByAdmin(UserRequest request);

    SimpleResponse userApp(UserRequest request);

    List<UserResponse> jobApplication(Long id, String word);

    SimpleResponse assignUserToRest(Long userId, Long restId);
    UserResponse getById(Long userId);

    List<UserResponse> getAll(Long restId);
    SimpleResponse update(Long userId, UserRequest request);

    SimpleResponse deleteById(Long userId);
}
