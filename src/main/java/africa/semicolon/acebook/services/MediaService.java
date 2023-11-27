package africa.semicolon.acebook.services;

import africa.semicolon.acebook.dtos.request.AddFriendRequest;
import africa.semicolon.acebook.dtos.request.AddMediaRequest;
import africa.semicolon.acebook.dtos.response.AddMediaResponse;
import org.springframework.web.multipart.MultipartFile;

public interface MediaService {

    default AddMediaRequest buildAddMediaRequest(String uploader, MultipartFile file, String description){
        AddMediaRequest request = new AddMediaRequest();
        request.setUploader(uploader);
        request.setMedia(file);
        request.setDescription(description);
        return request;
    }
    AddMediaResponse addMedia(AddMediaRequest addMediaRequest);

    //TODO: Add getMediaForUser that returns a user's media collection
}
