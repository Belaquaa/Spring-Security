package belaquaa.useredit.service;

import belaquaa.useredit.model.Role;
import belaquaa.useredit.model.User;
import belaquaa.useredit.repository.RoleRepository;
import belaquaa.useredit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userDao;
    private final RoleRepository roleDao;

    @Transactional
    public void addUser(User user) {
        Role userRole = roleDao.findByRole("ROLE_USER").orElseThrow(() -> new RuntimeException("Role 'user' not found"));
        user.getRoles().add(userRole);
        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        userDao.save(user);
    }

    @Transactional(readOnly = true)
    public List<User> listUsers() {
        return userDao.findAll();
    }

    @Transactional(readOnly = true)
    public User findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userDao.findByEmail(email).orElse(null);
    }

    @Transactional
    public void updateUser(Long id, User updatedUser) {
        User existingUser = userDao.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setEmail(updatedUser.getEmail());
        userDao.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteUserRolesByUserId(id);
        userDao.deleteById(id);
    }

    @Transactional
    public void addRoleToUser(Long userId, Long roleId) {
        User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleDao.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().add(role);
        userDao.save(user);
    }

    @Transactional
    public void removeRoleFromUser(Long userId, Long roleId) {
        User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        Role role = roleDao.findById(roleId).orElseThrow(() -> new RuntimeException("Role not found"));
        user.getRoles().remove(role);
        userDao.save(user);
    }

    @Transactional
    public void addRole(String roleName) {
        Role role = Role.builder().role(roleName).build();
        roleDao.save(role);
    }

    @Transactional(readOnly = true)
    public Role findRoleByName(String roleName) {
        return roleDao.findByRole(roleName).orElse(null);
    }
}


