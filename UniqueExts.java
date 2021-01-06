import java.io.*;
import java.util.*;

public class UniqueExts
{
    public Set<String> scan() {
        return scan(".", false);
    }

    public Set<String> scan(String dir) {
        return scan(dir, false);
    }

    public Set<String> scan(String dir, boolean recursive) {
        File directory = new File(dir);
        if( !directory.isDirectory() ) {
            System.err.println("Not a directory: " + dir);
            return null;
        }

        Set<String> extensions = new TreeSet<String>();
        File[] entries = directory.listFiles();

        for(File f : entries) {
            if( f.isDirectory() && recursive) {
              extensions.addAll(scan(f.getAbsolutePath(), true));
            } else {
                Optional<String> ext = getExtension(f.getName());
                if( ext != null && ext.isPresent() ) {
                    extensions.add(ext.get());
                }
            }
        }

        return extensions;
    }

    public Optional<String> getExtension(String filename) {
        return Optional.ofNullable(filename)
          .filter(f -> f.contains("."))
          .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    public static void main(String[] argv) {
        UniqueExts exts = new UniqueExts();
        Set<String> result = null;

        if( argv.length < 1 ) {
            result = exts.scan();
        }
        else if( argv.length < 2 ) {
            result = exts.scan(argv[0]);
        }
        else {
            result = exts.scan(argv[0], true);
        }

        System.out.println("Found " + result.size() + " extensions.");
        for(String s : result) {
            System.out.println(s);
        }
    }
}
