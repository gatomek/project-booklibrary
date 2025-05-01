package pl.gatomek.booklibrary.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.annotation.Validated;
import pl.gatomek.booklibrary.validator.custom.FolderPathConstraint;

@Getter
@Setter
@Configuration
@Validated
@ConfigurationProperties(prefix = "booklib")
public class BookLibConfig {

    @FolderPathConstraint
    private String folderPath;
}
