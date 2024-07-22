package belaquaa.useredit.vkapi;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class VkApiResponse {
    private List<VkUser> response;
}

