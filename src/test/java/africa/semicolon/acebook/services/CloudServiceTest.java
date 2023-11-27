package africa.semicolon.acebook.services;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static africa.semicolon.acebook.utils.TestUtils.getTestImage;
import static africa.semicolon.acebook.utils.AppUtils.IMAGE_LOCATION;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@Slf4j
public class CloudServiceTest {

    @Autowired
    private CloudService cloudService;

    @Test
    public void uploadImageTest() {
        MultipartFile image = getTestImage(IMAGE_LOCATION);
        String response = cloudService.upload(image);
        assertThat(response).isNotNull();
    }

    @Test
    public void testUploadVideo(){
        String fileLocation = "C:\\Users\\semicolon\\Documents\\spring_projects\\acebook\\acebook\\src\\main\\resources\\media\\Louis Dunford - The Angel.mp4";
        Path path = Paths.get(fileLocation);

        try(InputStream stream = Files.newInputStream(path)){
            MultipartFile image = new MockMultipartFile("the angel", stream);
            String response = cloudService.upload(image);

            log.info("url-->{}", response);
            assertThat(response).isNotNull();
        }catch (Exception exception){
            exception.printStackTrace();
        }
    }
}
