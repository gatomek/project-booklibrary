package pl.gatomek.booklibrary.validator.custom;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FolderPathValidator implements ConstraintValidator<FolderPathConstraint, String> {
    @Override
    public boolean isValid(String folderPath, ConstraintValidatorContext constraintValidatorContext) {
        if (folderPath == null)
            return false;
        
        Path folder = Paths.get(folderPath);
        return Files.exists(folder);
    }
}
