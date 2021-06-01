package test.utilities;

import java.util.logging.Level;
import java.util.logging.LogRecord;
import javax.annotation.Nonnull;

public final class LogData {
    @Nonnull
    private final Level level;
    @Nonnull
    private final String message;

    static LogData of(LogRecord record) {
        return new LogData(record.getLevel(), record.getMessage());
    }

    public LogData(@Nonnull Level level, @Nonnull String message) {
        this.level = level;
        this.message = message;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof LogData)) {
            return false;
        }

        var logData = (LogData) obj;
        return level.equals(logData.level) && message.equals((logData.message));
    }

    @Override
    public int hashCode() {
        return level.hashCode() * 31 + message.hashCode();
    }

    @Override
    public String toString() {
        return "\"%s\" @ %s".formatted(message, level);
    }
}
