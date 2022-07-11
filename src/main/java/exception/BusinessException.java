package exception;

/**
 * The type Business exception.
 */
public class BusinessException extends Exception {

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
    public BusinessException(final String codeError, final String messageError) {
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
