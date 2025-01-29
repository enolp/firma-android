package es.gob.afirma.android.errors;

public class ErrorCategory {

    private int code;
    private String userText;
    private String adminText;

    public ErrorCategory(int code, String userText, String adminText) {
        this.code = code;
        this.userText = userText;
        this.adminText = adminText;
    }

    public int getCode() {
        return code;
    }

    public String getAdminText() {
        return adminText;
    }

    public String getUserText() {
        return userText;
    }

    public String getAdminMsg() {
        return "AA" + this.getCode() + " - " + this.getAdminText();
    }

    public String getUserMsg() {
        return "AA" + this.getCode() + " - " + this.getUserText();
    }

}
