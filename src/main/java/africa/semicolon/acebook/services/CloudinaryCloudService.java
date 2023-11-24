package africa.semicolon.acebook.services;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@Service
public class CloudinaryCloudService implements CloudService{
    @Override
    public String upload(MultipartFile file) {
        Cloudinary cloudinary = new Cloudinary(
                ObjectUtils.asMap(
                        "cloud_name", "dlvi5kpsr",
                        "api_key", "828148521634137",
                        "api_secret", "67Y0_KW-HRiWfLJq96J2q15_-g8"
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
