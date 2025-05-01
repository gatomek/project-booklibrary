package pl.gatomek.booklibrary.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import pl.gatomek.booklibrary.dto.LibraryItem;
import pl.gatomek.booklibrary.library.Library;

import java.io.FileNotFoundException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@Service
public class BookLibService {

    private final Library library;

    public LibraryItem resolve(String hash) throws FileNotFoundException {
        return library.resolve(hash);
    }

    public List<LibraryItem> list() {
        return library.list();
    }

    public void reload() {
        library.reload();
    }

    public LibraryResource getBook(String hash) throws IOException {
        LibraryItem item = library.resolve(hash);

        Path path = Paths.get(item.filePath());
        ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(path));

        return LibraryResource.of(path, resource);
    }
}
