package io.mysearch.businesssearch.clients;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import feign.Response;
import feign.codec.ErrorDecoder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.util.MimeTypeUtils;

import java.io.IOException;
import java.util.Optional;

/**
 * Feign client error decoder.
 * Translates error responses into {@link FeignClientException}.
 */
@RequiredArgsConstructor
public class FeignClientErrorDecoder implements ErrorDecoder {

  private final XmlMapper mapper;

  @Override
  public Exception decode(String methodKey, Response response) {
    final var contentType = getContentType(response);

    var error = switch (contentType) {
      case MimeTypeUtils.APPLICATION_XML_VALUE -> getXmlError(response);
      default -> throw new RuntimeException(String.format("Unable to decode error of type '%s'", contentType));
    };

    return new FeignClientException(error);
  }

  private FeignClientError getXmlError(Response response) {
    try {
      return mapper.readValue(response.body().asInputStream(), FeignClientError.class);
    } catch (IOException ex) {
      throw new RuntimeException("Error deserializing XML of feign client error.", ex);
    }
  }

  private String getContentType(Response response) {
    return Optional.ofNullable(response.headers().get(HttpHeaders.CONTENT_TYPE))
      .flatMap(collection -> collection.stream().findFirst())
      .map(contentType -> contentType.split(";")[0])
      .orElse(null);
  }
}
