package io.mysearch.businesssearch.clients;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Model for feign client error response.
 * Error responses are being received in XML format, thus the model is being annotated using JAXB annotations to assist
 * deserialization and serialization (for testing).
 * <p>
 * TODO investigate how to make this immutable. Due to how XML is being deserialized this required to have setters.
 */
@XmlRootElement(name = "Error")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class FeignClientError {

  @XmlElement(name = "Code")
  String code;

  @XmlElement(name = "Message")
  String message;

  @XmlElement(name = "Details")
  String details;
}