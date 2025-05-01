package pl.gatomek.booklibrary.rest;

import lombok.Getter;
import org.springframework.http.MediaType;

@Getter
public enum FileExtensionToMediaType {
    pdf(MediaType.APPLICATION_PDF),
    jpeg(MediaType.IMAGE_JPEG),
    jpg(MediaType.IMAGE_JPEG);

    private final MediaType mediaType;

    FileExtensionToMediaType(MediaType mediaType) {
        this.mediaType = mediaType;
    }
}
