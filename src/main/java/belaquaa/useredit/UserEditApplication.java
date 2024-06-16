package belaquaa.useredit;

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

        userService.addUser(User.builder()
                .name("Vlad")
                .age(22)
                .email("vladdosiik@mail.ru")
                .build());

        userService.addUser(User.builder()
                .name("Belaquaa")
                .age(19)
                .email("eee_pokkk@mail.ru")
                .build());

        userService.addUser(User.builder()
                .name("Geralt")
                .age(38)
                .email("just.fun.now228@gmail.com")
                .build());
    }
}
