package es.gob.afirma.android;

/** Clase que contiene los datos le&iacute;dos en el registro de firmas para mostrarlos en la vista. */
public class SignRecord {

    private String signDate;
    private String signType;
    private String fileName;
    private String appName;
    private String signOperation;

    public SignRecord(String signDate, String signType, String signOperation, String fileName, String appName) {
        this.signDate = signDate;
        this.signType = signType;
        this.signOperation = signOperation;
        this.fileName = fileName;
        this.appName = appName;

    }

    public String getSignDate() {
        return signDate;
    }

    public String getSignType() {
        return signType;
    }

    public String getFileName() {
        return fileName;
    }

    public String getSignOperation() {
        return signOperation;
    }

    public String getAppName() {
        return appName;
    }

}
