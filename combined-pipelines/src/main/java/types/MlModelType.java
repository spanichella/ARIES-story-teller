package types;

import javax.annotation.Nonnull;

public enum MlModelType implements IDescribable {
    J48("J48"), PART("PART"), NAIVE_BAYES("NaiveBayes"), IB_K("IBk"), ONE_R("OneR"), SMO("SMO"),
    LOGISTIC("Logistic"), ADA_BOOST_M1("AdaBoostM1"), LOGIT_BOOST("LogitBoost"), DECISION_STUMP("DecisionStump"),
    LINEAR_REGRESSION("LinearRegression"), REGRESSION_BY_DISCRETIZATION("RegressionByDiscretization");

    @Nonnull
    private final String description;

    MlModelType(@Nonnull String description) {
        this.description = description;
    }

    @Nonnull
    @Override
    public String getDescription() {
        return description;
    }
}
