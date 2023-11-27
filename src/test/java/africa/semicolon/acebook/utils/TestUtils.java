package africa.semicolon.acebook.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class TestUtils {

    public static MultipartFile getTestImage(String fileLocation){
        Path path = Paths.get(fileLocation);
        try(InputStream stream = Files.newInputStream(path)) {
            MultipartFile file= new MockMultipartFile("test file", stream);
            return file;
        }catch (Exception exception){
            log.error("Error obtaining file");
            return null;
        }
    }
}
