package org.x1c1b.poll4u.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class AlreadyVotedException extends RuntimeException {

    public AlreadyVotedException() {
        super();
    }

    public AlreadyVotedException(String message) {
        super(message);
    }

    public AlreadyVotedException(String message, Throwable cause) {
        super(message, cause);
    }

    public AlreadyVotedException(Throwable cause) {
        super(cause);
    }

    protected AlreadyVotedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
