package belaquaa.useredit.vkapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkApiResponse {

    @JsonProperty("response")
    private List<VkUser> users;

    @Data
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class VkUser {

        @JsonProperty("photo_200")
        private String photo200;
    }
}

