package pl.gatomek.booklibrary.service;

import org.springframework.stereotype.Service;
import pl.gatomek.booklibrary.config.BookLibConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

@Service
public class BookLibService {

    private final BookLibConfig bookLibConfig;

    public BookLibService(BookLibConfig bookLibConfig) {
        this.bookLibConfig = bookLibConfig;
    }

    public void openBook(String fileName) throws IOException {
        String filePath = bookLibConfig.getFolderPath() + "\\" + fileName;

        File file = new File(filePath);
        if (file.exists()) {
            Runtime.getRuntime().exec(new String[]{"explorer", file.getAbsolutePath()});
        } else {
            throw new FileNotFoundException(fileName);
        }
    }
}
