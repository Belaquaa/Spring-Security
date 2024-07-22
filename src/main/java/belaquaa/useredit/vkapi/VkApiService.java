package belaquaa.useredit.vkapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class VkApiService {

    private final RestTemplate restTemplate;

    @Value("${vk.api.version}")
    private String apiVersion;

    @Value("${vk.api.access-token}")
    private String accessToken;

    public VkApiService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public String getUserProfilePicture(String userId) {
        String url = String.format("https://api.vk.com/method/users.get?user_ids=%s&fields=photo_200&access_token=%s&v=%s",
                userId, accessToken, apiVersion);

        VkApiResponse response = restTemplate.getForObject(url, VkApiResponse.class);
        if (response != null && !response.getResponse().isEmpty()) {
            return response.getResponse().get(0).getPhoto200();
        }
        return null;
    }
}