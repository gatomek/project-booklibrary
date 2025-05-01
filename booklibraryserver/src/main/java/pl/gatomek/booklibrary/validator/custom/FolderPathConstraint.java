package pl.gatomek.booklibrary.validator.custom;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FolderPathValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface FolderPathConstraint {
    String message() default "Given Book Library Folder Not Found";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
