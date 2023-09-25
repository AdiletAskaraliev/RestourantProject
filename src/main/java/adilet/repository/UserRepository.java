package adilet.repository;

import adilet.dto.response.UserResponse;
import adilet.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> getUserByEmail(String email);

    Boolean existsByEmail(String email);

    Boolean existsByPhoneNumber(String phoneNumber);
    Optional<User> findByEmail(String email);
    @Query("select new adilet.dto.response.UserResponse(l.id,concat(l.firstname, l.lastname),l.dateOfBirth,l.email,l.phoneNumber,l.experience) from User l where l.restaurant.id=:resId")
    List<UserResponse> getAllUsers(Long resId);
    @Query("select new adilet.dto.response.UserResponse(l.id,concat(l.firstname, l.lastname),l.dateOfBirth,l.email,l.phoneNumber,l.experience) from User l where l.id=:id")
    Optional<UserResponse> getUserById(Long id);
    @Query("select new adilet.dto.response.UserResponse(l.id,concat(l.firstname, l.lastname),l.dateOfBirth,l.email,l.phoneNumber,l.experience) from User l where l.restaurant.id=null")
    List<UserResponse> getAllApp();
}
