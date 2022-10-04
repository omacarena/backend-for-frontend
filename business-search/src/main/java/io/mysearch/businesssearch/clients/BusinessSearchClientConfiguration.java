package io.mysearch.businesssearch.clients;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import feign.codec.ErrorDecoder;
import org.springframework.context.annotation.Bean;

/**
 * Business search client feign client configuration.
 */
public class BusinessSearchClientConfiguration {

  @Bean
  public ErrorDecoder errorDecoder(XmlMapper mapper) {
    return new FeignClientErrorDecoder(mapper);
  }
}
