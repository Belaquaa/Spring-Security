package belaquaa.useredit.vkapi;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class VkApiService {

    private static final Logger logger = LoggerFactory.getLogger(VkApiService.class);

    private final RestTemplate restTemplate;
    private final VkApiProperties vkApiProperties;

    public String getUserProfilePicture(String userId) {
        String url = String.format("https://api.vk.com/method/users.get?user_ids=%s&fields=photo_200&access_token=%s&v=%s",
                userId, vkApiProperties.getAccessToken(), vkApiProperties.getVersion());

        try {
            VkApiResponse response = restTemplate.getForObject(url, VkApiResponse.class);
            if (response != null && response.getUsers() != null && !response.getUsers().isEmpty()) {
                return response.getUsers().get(0).getPhoto200();
            }
        } catch (HttpClientErrorException e) {
            logger.error("HTTP error while fetching user profile picture: {}", e.getStatusCode());
        } catch (Exception e) {
            logger.error("Error while fetching user profile picture", e);
        }
        return null;
    }
}