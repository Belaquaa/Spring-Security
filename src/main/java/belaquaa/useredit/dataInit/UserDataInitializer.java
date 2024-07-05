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

    private UserService userService;

    @PostConstruct
    public void init() {
        createUsers();
    }

    public void createUsers() {
        // Добавление ролей
        for (Roles role : Roles.values()) {
            userService.addRole(role.name());
        }

        // Создание пользователей
        User vlad = User.builder()
                .username("Vlad")
                .age(22)
                .email("vladdosiik@mail.ru")
                .password("123a")
                .build();
        userService.addUser(vlad);

        User belaquaa = User.builder()
                .username("Belaquaa")
                .age(19)
                .email("eee_pokkk@mail.ru")
                .password("123")
                .build();
        userService.addUser(belaquaa);

        User geralt = User.builder()
                .username("Geralt")
                .age(38)
                .email("just.fun.now228@gmail.com")
                .password("123")
                .build();
        userService.addUser(geralt);

        User test = User.builder()
                .username("test")
                .age(11)
                .email("test@mail.ru")
                .password("test")
                .build();
        userService.addUser(test);

        // Добавление ролей к пользователям
        Role adminRole = userService.findRoleByName("ROLE_ADMIN");
        Role testerRole = userService.findRoleByName("ROLE_TESTER");

        if (adminRole != null) {
            userService.addRoleToUser(test.getId(), adminRole.getId());
            userService.addRoleToUser(vlad.getId(), adminRole.getId());
        }

        if (testerRole != null) {
            userService.addRoleToUser(geralt.getId(), testerRole.getId());
        }
    }
}