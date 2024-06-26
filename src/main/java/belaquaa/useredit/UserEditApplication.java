package belaquaa.useredit;

import belaquaa.useredit.model.Role;
import belaquaa.useredit.model.User;
import belaquaa.useredit.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("belaquaa")
public class UserEditApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(UserEditApplication.class, args);
        UserService userService = context.getBean(UserService.class);

        // Создание ролей
        userService.addRole("user");
        userService.addRole("admin");
        userService.addRole("tester");

        // Создание пользователей
        User vlad = User.builder()
                .name("Vlad")
                .age(22)
                .email("vladdosiik@mail.ru")
                .password("123a")
                .build();
        userService.addUser(vlad);

        User belaquaa = User.builder()
                .name("Belaquaa")
                .age(19)
                .email("eee_pokkk@mail.ru")
                .password("123")
                .build();
        userService.addUser(belaquaa);

        User geralt = User.builder()
                .name("Geralt")
                .age(38)
                .email("just.fun.now228@gmail.com")
                .password("123")
                .build();
        userService.addUser(geralt);

        User test = User.builder()
                .name("test")
                .age(11)
                .email("test@mail.ru")
                .password("test")
                .build();
        userService.addUser(test);

        // Присваивание дополнительных ролей пользователям
        Role adminRole = userService.findRoleByName("admin");
        Role testerRole = userService.findRoleByName("tester");

        if (adminRole != null) {
            userService.addRoleToUser(test.getId(), adminRole.getId());
            userService.addRoleToUser(vlad.getId(), adminRole.getId());
        }

        if (testerRole != null) {
            userService.addRoleToUser(geralt.getId(), testerRole.getId());
        }
    }
}