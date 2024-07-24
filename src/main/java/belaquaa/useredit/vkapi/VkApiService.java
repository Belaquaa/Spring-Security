package belaquaa.useredit.vkapi;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Service
@RequiredArgsConstructor
@Slf4j
public class VkApiService {

    private static final String VK_API_URL =
            "https://api.vk.com/method/users.get?user_ids=%s&fields=photo_200&access_token=%s&v=%s";

    private final WebClient webClient;
    private final VkApiProperties vkApiProperties;

    public String getUserProfilePicture(String userId) {
        String url = String.format(VK_API_URL, userId, vkApiProperties.getAccessToken(), vkApiProperties.getVersion());

        try {
            VkApiResponse response = webClient.get()
                    .uri(url)
                    .retrieve()
                    .bodyToMono(VkApiResponse.class)
                    .block();  // Либо .subscribe() для асинхронности

            if (response != null && response.getUsers() != null && !response.getUsers().isEmpty()) {
                return response.getUsers().get(0).getPhoto200();
            }
        } catch (WebClientResponseException e) {
            log.error("HTTP error while fetching user profile picture: {}", e.getStatusCode());
        } catch (Exception e) {
            log.error("Error while fetching user profile picture", e);
        }
        return null;
    }
}