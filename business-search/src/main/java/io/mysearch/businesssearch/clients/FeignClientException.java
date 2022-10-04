package io.mysearch.businesssearch.clients;

import lombok.Getter;

/**
 * Exception thrown if feign client returns error codes.
 */
@Getter
public class FeignClientException extends RuntimeException {

  private final FeignClientError error;

  public FeignClientException(FeignClientError error) {
    super(String.format("Feign client error '%s' occurred.", error.getCode()));
    this.error = error;
  }
}
