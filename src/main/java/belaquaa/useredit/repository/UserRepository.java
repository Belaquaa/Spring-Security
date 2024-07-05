package belaquaa.useredit.repository;

import belaquaa.useredit.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Modifying
    @Transactional
    @Query(value = "DELETE FROM user_roles WHERE user_id = :userId", nativeQuery = true)
    void deleteUserRolesByUserId(@Param("userId") Long userId);

    Optional<User> findByEmail(String email);
}