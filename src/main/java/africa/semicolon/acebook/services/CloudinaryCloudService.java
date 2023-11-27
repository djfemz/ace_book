package africa.semicolon.acebook.services;

import africa.semicolon.acebook.config.AppConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
public class CloudinaryCloudService implements CloudService{
    private final AppConfig appConfig;

    @Override
    public String upload(MultipartFile file) {
        Cloudinary cloudinary = new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", appConfig.getCloudApiName(),
                        "api_key", appConfig.getCloudApiKey(),
                        "api_secret", appConfig.getCloudApiSecret()
                )
        );
        try {
            Map<?,?> response = cloudinary.uploader().upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            return (String)response.get("secure_url");
        }catch (IOException exception){
            exception.printStackTrace();
            return null;
        }

    }
}
