package africa.semicolon.acebook.dtos.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class PremiumSubscriptionRequest {
    private Long accountId;
    private MultipartFile file;
}
