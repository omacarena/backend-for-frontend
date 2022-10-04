package io.mysearch.businesssearch.clients;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * Known feign client error codes which are received on response.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class FeignClientErrorCode {

  public static final String NO_SUCH_KEY = "NoSuchKey";
}
