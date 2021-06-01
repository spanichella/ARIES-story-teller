package types;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public enum PipelineType {
    ML("ML"), DL("DL");

    @Nonnull
    private final String description;

    PipelineType(@Nonnull String description) {
        this.description = description;
    }

    @Nonnull
    public String getDescription() {
        return description;
    }

    @Nullable
    public static PipelineType from(@Nullable String description) {
        if (description == null) {
            return null;
        }
        for (PipelineType type : values()) {
            if (type.description.equals(description)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Unknown PipelineType with description \"%s\"".formatted(description));
    }
}
