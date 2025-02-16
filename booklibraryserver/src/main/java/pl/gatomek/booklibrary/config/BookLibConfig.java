package pl.gatomek.booklibrary.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BookLibConfig {

    @Value("${bookLib.folderPath}")
    private String folderPath;

    public String getFolderPath() {
        return folderPath;
    }
}
