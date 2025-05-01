package pl.gatomek.booklibrary.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import pl.gatomek.booklibrary.dto.LibraryItem;
import pl.gatomek.booklibrary.service.BookLibService;
import pl.gatomek.booklibrary.service.LibraryResource;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.nio.file.Path;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class BookLibRestController {

    private static final String HASH = "hash";
    private static final String ATTR_HASH_EXPECTED = "Attribute hash expected";

    private final BookLibService bookLibService;

    @GetMapping(path = "/book/{hash}")
    public ResponseEntity<ByteArrayResource> getBook(@PathVariable String hash) throws IOException {
        LibraryResource resource = bookLibService.getBook(hash);
        String ext = getExtension(resource.path());
        FileExtensionToMediaType fileExt = FileExtensionToMediaType.valueOf(ext);

        return ResponseEntity.ok().contentType(fileExt.getMediaType()).body(resource.resource());
    }

    @PostMapping(path = "/resolve", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public LibraryItem resolve(@RequestBody MultiValueMap<String, String> formData) throws IOException {
        List<String> strings = formData.get(HASH);
        if (strings != null) {
            String hash = strings.getFirst();
            return bookLibService.resolve(hash);
        }

        throw new InvalidObjectException(ATTR_HASH_EXPECTED);
    }

    @GetMapping(path = "/list")
    public List<LibraryItem> list() {
        return bookLibService.list();
    }

    @GetMapping(path = "/reload")
    public void reload() {
        bookLibService.reload();
    }

    private String getExtension(Path path) {
        String fileName = path.getFileName().toString();
        int dotIndex = fileName.lastIndexOf('.');

        if (dotIndex == -1 || dotIndex == fileName.length() - 1)
            return "";
        else
            return fileName.substring(dotIndex + 1).toLowerCase();
    }
}
