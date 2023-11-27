package africa.semicolon.acebook.services;

import africa.semicolon.acebook.config.AppConfig;
import com.cloudinary.Cloudinary;
import com.cloudinary.Uploader;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
@AllArgsConstructor
@Slf4j
public class CloudinaryCloudService implements CloudService{
    private final Cloudinary cloudinary;

    @Override
    public String upload(MultipartFile file) {
        try {
            Uploader uploader = cloudinary.uploader();
            Map<?,?> response = uploader.upload(file.getBytes(),
                    ObjectUtils.asMap("resource_type", "auto"));
            log.info("cloudinary response-->{} ", response);
            return (String)response.get("secure_url");
        }catch (IOException exception){
            log.error("ERROR:: {}", exception.getMessage());
            return null;
        }

    }
}
