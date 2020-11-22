package oracle;

/**
 * @author panc
 */
public abstract class Oracle {

    protected static String dataType;

    protected static String nameOfAttributeID;

    protected static String nameOfAttributeText;

    protected static String nameOfAttributeClass;

    public static String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public static String getNameOfAttributeID() {
        return nameOfAttributeID;
    }

    public void setNameOfAttributeID(String nameOfAttributeID) {
        this.nameOfAttributeID = nameOfAttributeID;
    }

    public static String getNameOfAttributeText() {
        return nameOfAttributeText;
    }

    public void setNameOfAttributeText(String nameOfAttributeText) {
        this.nameOfAttributeText = nameOfAttributeText;
    }

    public static String getNameOfAttributeClass() {
        return nameOfAttributeClass;
    }

    public void setNameOfAttributeClass(String nameOfAttributeClass) {
        this.nameOfAttributeClass = nameOfAttributeClass;
    }

    public Oracle(String dataType, String nameOfAttributeID, String nameOfAttributeText, String nameOfAttributeClass) {
        this.dataType = dataType;
        this.nameOfAttributeID = nameOfAttributeID;
        this.nameOfAttributeText = nameOfAttributeText;
        this.nameOfAttributeClass = nameOfAttributeClass;
    }


}
