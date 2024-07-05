package belaquaa.useredit;

import belaquaa.useredit.model.Role;
import belaquaa.useredit.model.Roles;
import belaquaa.useredit.model.User;
import belaquaa.useredit.service.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.Arrays;

@SpringBootApplication
@ComponentScan("belaquaa")
public class UserEditApplication {

    private UserService userService;

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(UserEditApplication.class, args);
    }
}