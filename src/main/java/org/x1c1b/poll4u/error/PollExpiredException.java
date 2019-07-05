package org.x1c1b.poll4u.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GONE)
public class PollExpiredException extends RuntimeException {

    public PollExpiredException() {
        super();
    }

    public PollExpiredException(String message) {
        super(message);
    }

    public PollExpiredException(String message, Throwable cause) {
        super(message, cause);
    }

    public PollExpiredException(Throwable cause) {
        super(cause);
    }

    protected PollExpiredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
