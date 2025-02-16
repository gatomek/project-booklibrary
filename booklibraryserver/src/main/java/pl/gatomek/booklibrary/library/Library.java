package pl.gatomek.booklibrary.library;

import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import pl.gatomek.booklibrary.config.BookLibConfig;
import pl.gatomek.booklibrary.dto.LibraryItem;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Component
public class Library {

    private final BookLibConfig bookLibConfig;

    private TreeMap<String, String> archive = new TreeMap<>();

    public Library(BookLibConfig bookLibConfig) {
        this.bookLibConfig = bookLibConfig;

        reload();
    }

    private String calcMD5FromFileContent(File file) throws IOException {
        Path path = Paths.get(file.toURI());
        byte[] bytes = Files.readAllBytes(path);
        return DigestUtils.md5DigestAsHex(bytes).toUpperCase();
    }

    private void scanFolder(TreeMap<String, String> map, File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File f : files) {
                if (f.isFile()) {
                    try {
                        String hash = calcMD5FromFileContent(f);

                        String fp = map.get(hash);
                        if (fp != null) {
                            // todo: log duplicated file
                        }

                        map.put(hash, f.getAbsolutePath());
                    } catch (IOException ex) {
                        // todo: log io exception
                    }
                } else if (f.isDirectory()) {
                    scanFolder(map, f);
                } else {
                    // todo: log unexpected file type
                }
            }
        }
    }

    synchronized public void reload() {
        TreeMap<String, String> map = new TreeMap<>();

        File folder = new File(bookLibConfig.getFolderPath());
        if (folder.exists())
            scanFolder(map, folder);

        archive = map;
    }

    public LibraryItem resolve(String hash) throws FileNotFoundException {
        String filePath = archive.get(hash);
        if (filePath == null) {
            reload();
            filePath = archive.get(hash);
        }

        if (filePath != null)
            return new LibraryItem(hash, filePath);

        throw new FileNotFoundException(hash);
    }

    public List<LibraryItem> list() {
        // todo: try to use stream
        List<LibraryItem> list = new ArrayList<>();
        for (var e : archive.entrySet())
            list.add(new LibraryItem(e.getKey(), e.getValue()));
        return list;
    }
}
