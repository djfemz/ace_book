package africa.semicolon.acebook.controllers;

import africa.semicolon.acebook.dtos.request.AddMediaRequest;
import africa.semicolon.acebook.dtos.response.AddMediaResponse;
import africa.semicolon.acebook.services.MediaService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import static org.springframework.http.MediaType.MULTIPART_FORM_DATA_VALUE;

@RestController
@RequestMapping("/api/v1/media")
@AllArgsConstructor
public class MediaController {

    private final MediaService mediaService;

    @PostMapping(consumes = {MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<AddMediaResponse> addMedia(@RequestPart String uploader,
                                                     @RequestPart("file") MultipartFile file,
                                                     @RequestPart String description){

        var response = mediaService.addMedia(mediaService.buildAddMediaRequest(uploader, file, description));
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
