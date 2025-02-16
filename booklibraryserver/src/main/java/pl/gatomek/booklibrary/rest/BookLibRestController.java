package pl.gatomek.booklibrary.rest;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.gatomek.booklibrary.dto.LibraryItem;
import pl.gatomek.booklibrary.service.BookLibService;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.List;

@RestController
public class BookLibRestController {

    private static final String HASH = "hash";
    private static final String ATTR_HASH_EXPECTED = "Attribute hash expected";

    private final BookLibService bookLibService;

    public BookLibRestController( BookLibService bookLibService) {
        this.bookLibService = bookLibService;
    }

    @PostMapping(path = "/resolve", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public LibraryItem resolve( @RequestBody MultiValueMap<String, String> formData) throws IOException {

        List<String> strings = formData.get(HASH);
        if( strings != null) {
            String hash = strings.getFirst();
            return bookLibService.resolve(hash);
        }

        throw new InvalidObjectException( ATTR_HASH_EXPECTED);
    }

    @GetMapping(path = "/list")
    public List<LibraryItem> list() {
        return bookLibService.list();
    }

    @GetMapping(path = "/reload")
    public void reload() {
        bookLibService.reload();
    }
}
