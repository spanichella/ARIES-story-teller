package types;

import javax.annotation.Nonnull;

@FunctionalInterface
public interface IDescribable {
    @Nonnull
    String getDescription();
}
