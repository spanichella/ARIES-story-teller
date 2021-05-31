package test.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.logging.Filter;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.stream.Collectors;

public final class LogMessageFetcher implements Filter {
    private final Collection<LogRecord> records = new ArrayList<>(10);

    @Override
    public boolean isLoggable(LogRecord record) {
        records.add(record);
        return false;
    }

    public Map<Level, String> getMessages() {
        return records.stream().collect(Collectors.toMap(LogRecord::getLevel, LogRecord::getMessage));
    }
}
