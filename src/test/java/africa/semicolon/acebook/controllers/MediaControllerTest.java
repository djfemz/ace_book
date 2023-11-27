package africa.semicolon.acebook.controllers;


import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.mock.web.MockPart;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;

import static africa.semicolon.acebook.utils.AppUtils.IMAGE_LOCATION;
import static africa.semicolon.acebook.utils.TestUtils.getTestImage;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
public class MediaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void addMediaTest() throws Exception {
        byte[] fileBytes = new byte[]{};
        MultipartFile file = getTestImage(IMAGE_LOCATION);
        if (file!=null) fileBytes = file.getBytes();
        mockMvc.perform(multipart("/api/v1/media")
                .part(new MockPart("uploader", "john@email.com".getBytes()))
                .file(new MockMultipartFile("file", fileBytes))
                .part(new MockPart("description", "test desc".getBytes())))
                .andExpect(status().isCreated())
                .andDo(print());
    }
}
