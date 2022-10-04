package io.mysearch.businesssearch.errors;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;

/**
 * Base exception from which other exception inherit.
 * Adds error code.
 */
@JsonIgnoreProperties({"stackTrace", "suppressed", "localizedMessage", "cause"})
@Getter
public class BaseException extends RuntimeException {

  private String code;

  public BaseException(String code) {
    this.code = code;
  }

  public BaseException(String message, String code) {
    super(message);
    this.code = code;
  }

  public BaseException(String message, Throwable cause, String code) {
    super(message, cause);
    this.code = code;
  }

  public BaseException(Throwable cause, String code) {
    super(cause);
    this.code = code;
  }

  public BaseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, String code) {
    super(message, cause, enableSuppression, writableStackTrace);
    this.code = code;
  }
}
