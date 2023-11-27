package africa.semicolon.acebook.services;


import africa.semicolon.acebook.dtos.request.AddMediaRequest;
import africa.semicolon.acebook.dtos.response.AddMediaResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.multipart.MultipartFile;

import static africa.semicolon.acebook.utils.TestUtils.getTestImage;
import static africa.semicolon.acebook.utils.AppUtils.IMAGE_LOCATION;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class MediaServiceTest {

    @Autowired
    private MediaService mediaService;

    @Test
    public void testAddMedia(){
        MultipartFile file=getTestImage(IMAGE_LOCATION);
        AddMediaRequest addMediaRequest = new AddMediaRequest();
        addMediaRequest.setUploader("john@email.com");
        addMediaRequest.setMedia(file);
        addMediaRequest.setDescription("My first media file");

        AddMediaResponse addMediaResponse = mediaService.addMedia(addMediaRequest);

        assertNotNull(addMediaResponse);
        assertNotNull(addMediaResponse.getMessage());
    }
}
