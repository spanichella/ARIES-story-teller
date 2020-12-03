package ml;


/**
 * @author panc
 */
public abstract class MachineLearningClassifier {

    protected static String classifierToolChain;
    protected static String machineLearningModelName;

    public static String getClassifierToolChain() {
        return classifierToolChain;
    }

    public void setClassifierToolChain(String classifierToolChain) {
        this.classifierToolChain = classifierToolChain;
    }

    public static String getMachineLearningModelName() {
        return machineLearningModelName;
    }

    public void setMachineLearningModelName(String machineLearningModelName) {
        this.machineLearningModelName = machineLearningModelName;
    }
}
