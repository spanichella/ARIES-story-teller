package types;

import javax.annotation.Nonnull;

public enum DataType implements IDescribable {
    REQUIREMENT_SPECIFICATIONS("Requirement-Specifications"), USER_REVIEWS("User-Reviews");

    @Nonnull
    private final String description;

    DataType(@Nonnull String description) {
        this.description = description;
    }

    @Nonnull
    @Override
    public String getDescription() {
        return description;
    }
}
