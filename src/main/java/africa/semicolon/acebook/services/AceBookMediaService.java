package africa.semicolon.acebook.services;

import africa.semicolon.acebook.dtos.request.AddMediaRequest;
import africa.semicolon.acebook.dtos.response.AddMediaResponse;
import africa.semicolon.acebook.models.Media;
import africa.semicolon.acebook.repositories.MediaRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AceBookMediaService implements MediaService{
    private final ModelMapper modelMapper;
    private final CloudService cloudService;
    private final MediaRepository mediaRepository;
    @Override
    public AddMediaResponse addMedia(AddMediaRequest addMediaRequest) {
        String url = cloudService.upload(addMediaRequest.getFile());
        Media media = modelMapper.map(addMediaRequest, Media.class);
        media.setUrl(url);
        Media savedMedia = mediaRepository.save(media);
        return buildAddMediaResponse(savedMedia);
    }

    private static AddMediaResponse buildAddMediaResponse(Media savedMedia) {
        AddMediaResponse addMediaResponse = new AddMediaResponse();
        addMediaResponse.setMessage("Media added successfully");
        addMediaResponse.setUrl(savedMedia.getUrl());
        addMediaResponse.setMediaId(savedMedia.getId());
        return addMediaResponse;
    }
}
