package types;

import javax.annotation.Nonnull;

public enum PipelineType implements IDescribable {
    ML("ML"), DL("DL");

    @Nonnull
    private final String description;

    PipelineType(@Nonnull String description) {
        this.description = description;
    }

    @Nonnull
    @Override
    public String getDescription() {
        return description;
    }
}
