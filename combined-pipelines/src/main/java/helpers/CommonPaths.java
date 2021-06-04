package helpers;

import java.nio.file.Path;

public final class CommonPaths {
    private static final Path MAIN = Path.of(System.getProperty("user.dir"));
    public static final Path PROJECT_ROOT = MAIN.getParent();
    public static final Path RESOURCES = MAIN.resolve("resources");
    public static final Path R_SCRIPTS = RESOURCES.resolve("R-scripts");
    public static final Path GLOVE_FILE = RESOURCES.resolve("DL").resolve("glove.6B.100d.txt");
}
