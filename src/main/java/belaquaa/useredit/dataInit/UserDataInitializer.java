package belaquaa.useredit.dataInit;

import belaquaa.useredit.model.Role;
import belaquaa.useredit.model.Roles;
import belaquaa.useredit.model.User;
import belaquaa.useredit.service.UserService;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserDataInitializer {

    private final UserService userService;

    @PostConstruct
    private void initializeData() {
        for (Roles role : Roles.values()) {
            userService.addRole(role.name());
        }

        createUser("Vlad", 22, "vladdosiik@mail.ru", "123a");
        createUser("Belaquaa", 19, "eee_pokkk@mail.ru", "123");
        createUser("Geralt", 38, "just.fun.now228@gmail.com", "123");
        createUser("NaN1", 11, "vovandrelo@mail.ru", "test");

        User vlad = userService.findByEmail("vladdosiik@mail.ru");
        User geralt = userService.findByEmail("just.fun.now228@gmail.com");
        User vovan = userService.findByEmail("vovandrelo@mail.ru");

        Role adminRole = userService.findRoleByName("ROLE_ADMIN");
        Role testerRole = userService.findRoleByName("ROLE_TESTER");

        if (adminRole != null) {
            userService.addRoleToUser(vovan.getId(), adminRole.getId());
            userService.addRoleToUser(vlad.getId(), adminRole.getId());
        }

        if (testerRole != null) {
            userService.addRoleToUser(geralt.getId(), testerRole.getId());
        }
    }

    private void createUser(String username, int age, String email, String password) {
        User user = User.builder()
                .username(username)
                .age(age)
                .email(email)
                .password(password)
                .build();
        userService.addUser(user);
    }
}

