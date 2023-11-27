package africa.semicolon.acebook.dtos.request;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class AddMediaRequest {
    private String uploader;
    private MultipartFile file;
    private String description;
    public void setUploader(String uploaderEmail) {
        uploader=uploaderEmail;
    }

    public void setMedia(MultipartFile file) {
        this.file = file;
    }

    public void setDescription(String description) {
        this.description=description;
    }
}
