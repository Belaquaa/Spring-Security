package belaquaa.useredit.vkapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkUser {
    private String id;
    private String firstName;
    private String lastName;

    @JsonProperty("photo_200")
    private String photo200;
}