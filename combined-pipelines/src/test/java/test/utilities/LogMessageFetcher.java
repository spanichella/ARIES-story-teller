package test.utilities;

import java.util.ArrayList;
import java.util.Collection;
import java.util.logging.Filter;
import java.util.logging.LogRecord;
import java.util.stream.Collectors;

public final class LogMessageFetcher implements Filter {
    private final Collection<LogRecord> records = new ArrayList<>(10);

    @Override
    public boolean isLoggable(LogRecord record) {
        records.add(record);
        return false;
    }

    public Iterable<LogData> getMessages() {
        return records.stream().map(LogData::of).collect(Collectors.toUnmodifiableList());
    }
}
