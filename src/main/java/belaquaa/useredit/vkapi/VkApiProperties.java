package belaquaa.useredit.vkapi;


import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "vk.api")
@Data
public class VkApiProperties {
    private String version;
    private String accessToken;
}