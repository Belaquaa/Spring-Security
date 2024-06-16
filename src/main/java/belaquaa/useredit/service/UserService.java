package belaquaa.useredit.service;

import belaquaa.useredit.model.User;
import belaquaa.useredit.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userDao;

    @Transactional
    public void addUser(User user) {
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
        userDao.save(existingUser);
    }

    @Transactional
    public void deleteUser(Long id) {
        userDao.deleteById(id);
    }
}

