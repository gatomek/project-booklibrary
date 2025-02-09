package pl.gatomek.booklibrary.rest;

import org.springframework.http.MediaType;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import pl.gatomek.booklibrary.service.BookLibService;

import java.io.IOException;
import java.io.InvalidObjectException;
import java.util.List;

@RestController
public class BookLibRestController {

    private final BookLibService bookLibService;

    public BookLibRestController( BookLibService bookLibService) {
        this.bookLibService = bookLibService;
    }

    @PostMapping(path = "/openbook", consumes = {MediaType.APPLICATION_FORM_URLENCODED_VALUE})
    public void openBook( @RequestBody MultiValueMap<String, String> formData) throws IOException {

        List<String> strings = formData.get("filename");
        if( strings != null) {
            String fileName = formData.get("filename").getFirst();
            bookLibService.openBook(fileName);
        }
        else {
            throw new InvalidObjectException( "Attribute filename expected");
        }

    }
}
