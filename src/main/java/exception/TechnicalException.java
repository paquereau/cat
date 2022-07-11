package exception;

/**
 * The type Technical exception.
 */
public class TechnicalException extends RuntimeException {

    /**
     * The Code error.
     */
    private final String codeError;

    /**
     * The Message error.
     */
    private final String messageError;

    /**
     * Instantiates a new Technical exception.
     *
     * @param codeError    the code error
     * @param messageError the message error
     */
    public TechnicalException(final String codeError, final String messageError) {
        this.codeError = codeError;
        this.messageError = messageError;
    }

    /**
     * Instantiates a new Technical exception.
     *
     * @param codeError    the code error
     * @param messageError the message error
     * @param e            the e
     */
    public TechnicalException(final String codeError, final String messageError, final Throwable e) {
        super(e);
        this.codeError = codeError;
        this.messageError = messageError;
    }

    /**
     * Gets code error.
     *
     * @return the code error
     */
    public String getCodeError() {
        return codeError;
    }

    /**
     * Gets message error.
     *
     * @return the message error
     */
    public String getMessageError() {
        return messageError;
    }
}
