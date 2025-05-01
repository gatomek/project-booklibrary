package pl.gatomek.booklibrary.service;

import org.springframework.core.io.ByteArrayResource;

import java.nio.file.Path;

public record LibraryResource(Path path, ByteArrayResource resource) {
    public static LibraryResource of(Path path, ByteArrayResource resource) {
        return new LibraryResource(path, resource);
    }
}
