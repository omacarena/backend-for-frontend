package io.mysearch.businesssearch.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.module.jaxb.JaxbAnnotationModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configures {@link XmlMapper} for XML serialization/deserialization.
 */
@Configuration
public class XmlMapperConfiguration {

  @Bean
  public XmlMapper xmlMapper() {
    final var mapper = new XmlMapper();

    // considers JAXB annotations
    mapper.registerModule(new JaxbAnnotationModule());

    // ignore unknown properties
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    return mapper;
  }
}
