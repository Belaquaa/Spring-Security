package belaquaa.useredit.service;

import belaquaa.useredit.model.Role;
import belaquaa.useredit.model.User;
import belaquaa.useredit.repository.RoleRepository;
import belaquaa.useredit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
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
        Role userRole = roleDao.findByRole("user").orElseThrow(() -> new RuntimeException("Role 'user' not found"));
        user.getRoles().add(userRole);
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

    @Transactional
    public void updateUser(Long id, User updatedUser) {
        User existingUser = userDao.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setName(updatedUser.getName());
        existingUser.setAge(updatedUser.getAge());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setRoles(updatedUser.getRoles());
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


