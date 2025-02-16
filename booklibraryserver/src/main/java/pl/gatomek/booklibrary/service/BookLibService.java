package pl.gatomek.booklibrary.service;

import org.springframework.stereotype.Service;
import pl.gatomek.booklibrary.dto.LibraryItem;
import pl.gatomek.booklibrary.library.Library;

import java.io.FileNotFoundException;

import java.util.List;

@Service
public class BookLibService {

    private final Library library;

    public BookLibService(Library library) {
        this.library = library;
    }

    public LibraryItem resolve(String hash) throws FileNotFoundException {
        return library.resolve(hash);
    }

    public List<LibraryItem> list() {
        return library.list();
    }

    public void reload() {
        library.reload();
    }
}
