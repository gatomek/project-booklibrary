package pl.gatomek.booklibrary.dto;

public record LibraryItem(String hash, String filePath) {

    public static LibraryItem of(String hash, String filePath) {
        return new LibraryItem(hash, filePath);
    }
}
