package types;

import javax.annotation.Nonnull;

public enum StrategyType implements IDescribable {
    TEN_FOLD("10-Fold"), PERCENTAGE_SPLIT("Percentage-Split");

    @Nonnull
    private final String description;

    StrategyType(@Nonnull String description) {
        this.description = description;
    }

    @Nonnull
    @Override
    public String getDescription() {
        return description;
    }
}
