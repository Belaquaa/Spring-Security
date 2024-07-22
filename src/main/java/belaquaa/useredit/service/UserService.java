package belaquaa.useredit.service;

import belaquaa.useredit.exception.UserNotFoundException;
import belaquaa.useredit.exception.RoleNotFoundException;
import belaquaa.useredit.model.Role;
import belaquaa.useredit.model.User;
import belaquaa.useredit.repository.RoleRepository;
import belaquaa.useredit.repository.UserRepository;
import belaquaa.useredit.vkapi.VkApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${default.profile.picture.url}")
    private String DEFAULT_PROFILE_PICTURE_URL;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final VkApiService vkApiService;

    @Transactional
    public void addUser(User user) {
        Role userRole = roleRepository.findByRole("ROLE_USER")
                .orElseThrow(() -> new RoleNotFoundException("Role 'user' not found"));
        user.getRoles().add(userRole);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));

        setProfilePictureUrlBasedOnUsername(user);

        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    @Transactional
    public void updateUser(Long id, User updatedUser) {
        User existingUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setEmail(updatedUser.getEmail());

        String newProfilePictureUrl = generateProfilePictureUrl(updatedUser.getUsername());
        existingUser.setProfilePictureUrl(newProfilePictureUrl);

        userRepository.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userRepository.deleteUserRolesByUserId(id);
        userRepository.deleteById(id);
    }

    @Transactional
    public void addRoleToUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Transactional
    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
        user.getRoles().remove(role);
        userRepository.save(user);
    }

    @Transactional
    public void addRole(String roleName) {
        Role role = Role.builder().role(roleName).build();
        roleRepository.save(role);
    }

    @Transactional(readOnly = true)
    public Role findRoleByName(String roleName) {
        return roleRepository.findByRole(roleName)
                .orElseThrow(() -> new RoleNotFoundException("Role not found"));
    }

    private String generateProfilePictureUrl(String username) {
        return Optional.ofNullable(vkApiService.getUserProfilePicture(username))
                .orElse(DEFAULT_PROFILE_PICTURE_URL);
    }


    private void setProfilePictureUrlBasedOnUsername(User user) {
        String profilePictureUrl = generateProfilePictureUrl(user.getUsername());
        user.setProfilePictureUrl(profilePictureUrl);
    }

    @Transactional
    public void updateUserProfilePicture(Long userId, String profilePictureUrl) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        if (profilePictureUrl == null) {
            profilePictureUrl = DEFAULT_PROFILE_PICTURE_URL;
        }
        user.setProfilePictureUrl(profilePictureUrl);
        userRepository.save(user);
    }
}

