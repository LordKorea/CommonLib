package nge.lk.mods.commonlib.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;

/**
 * Utilities for interacting with the file system.
 */
public final class FileUtil {

    /**
     * Ensure that the given file exists.
     *
     * @param file The file.
     *
     * @return The file.
     */
    public static File ensureFileExists(final File file) {
        if (!file.exists()) {
            try {
                if (!file.createNewFile()) {
                    throw new IllegalStateException("file could not be created but operation finished without error");
                }
            } catch (final IOException e) {
                throw new IllegalStateException(e);
            }
        }
        return file;
    }

    /**
     * Read from a line storage file.
     *
     * @param file The file containing the line storage.
     * @param lineConsumer The consumer that accepts line tuples {@code (LineText, LineNumber)}.
     * @param versionConverter A converter for loading from old versions: {@code (Version, Original) -> Converted}.
     *
     * @throws IOException When reading fails.
     */
    public static void readLineStorage(final File file, final BiConsumer<String, Integer> lineConsumer,
                                       final BiFunction<Integer, String, String> versionConverter) throws IOException {
        ensureFileExists(file);

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            // The first line of line storage files contains a version number.
            final String versionLine = reader.readLine();
            if (versionLine == null) {
                return;
            }
            final int version = Integer.parseInt(versionLine.trim());

            int lineNo = 0;
            String line = reader.readLine();
            while (line != null) {
                if (line.isEmpty()) {
                    continue;
                }
                // 1) Convert.
                line = versionConverter.apply(version, line);

                // 2) Pass to the consumer.
                lineConsumer.accept(line, lineNo++);

                // 3) Read next line.
                line = reader.readLine();
            }
        }
    }

    /**
     * Write to a line storage file.
     *
     * @param version The version that is being written.
     * @param file The file containing the line storage.
     * @param lineSource The source of lines to store.
     *
     * @throws IOException When writing fails.
     */
    public static void writeLineStorage(final int version, final File file, final Iterator<String> lineSource)
            throws IOException {
        ensureFileExists(file);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            // Write the version number line.
            writer.write(Integer.toString(version));
            writer.write('\n');

            while (lineSource.hasNext()) {
                final String line = lineSource.next();
                if (line.isEmpty()) {
                    throw new IllegalStateException("Can't store empty lines in line storage");
                }
                writer.write(line);
                writer.write('\n');
            }
        }
    }

    /**
     * Private constructor to prevent instance creation
     */
    private FileUtil() {
    }
}
