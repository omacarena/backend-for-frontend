package io.mysearch.businesssearch.errors;

import lombok.Getter;

/**
 * Exception thrown if business was not found during search or querying.
 */
@Getter
public class BusinessNotFoundException extends BaseException {

  public BusinessNotFoundException() {
    super("Business not found", ErrorCode.BUSINESS_NOT_FOUND.name());
  }

  public BusinessNotFoundException(Throwable e) {
    super("Business not found", e, ErrorCode.BUSINESS_NOT_FOUND.name());
  }
}
