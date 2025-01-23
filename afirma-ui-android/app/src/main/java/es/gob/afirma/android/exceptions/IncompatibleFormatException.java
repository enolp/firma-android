package es.gob.afirma.android.exceptions;

import es.gob.afirma.android.errors.ErrorCategory;

public final class IncompatibleFormatException extends Exception {

    private static final long serialVersionUID = 1L;

    private ErrorCategory errorCat;

    public IncompatibleFormatException(ErrorCategory errorCat) {
        this.errorCat = errorCat;
    }

    public ErrorCategory getErrorCat() {
        return errorCat;
    }
}