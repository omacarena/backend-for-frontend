package io.mysearch.businesssearch.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import lombok.SneakyThrows;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.util.MimeTypeUtils;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static io.mysearch.businesssearch.utils.BusinessResponseFactory.createBusinessResponse;
import static io.mysearch.businesssearch.utils.BusinessResponseFactory.normalWeekHours;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {
  "feign.business-search.url=http://localhost:${wiremock.server.port}" + BusinessSearchClientContractTests.RESOURCE_PATH
})
@AutoConfigureWireMock(port = 0)
public class BusinessSearchClientContractTests {

  public static final String RESOURCE_PATH = "/wiremock";

  @Autowired
  BusinessSearchClient client;

  @Autowired
  ObjectMapper objectMapper;

  @Autowired
  XmlMapper xmlMapper;

  @LocalServerPort
  int serverPort;

  @Test
  @SneakyThrows
  public void get_by_id_when_id_does_not_exist_then_return_error() {
    var errorMessage = "some error";
    var errorDetails = "some details";

    var error = FeignClientError.builder()
      .code(FeignClientErrorCode.NO_SUCH_KEY)
      .message(errorMessage)
      .details(errorDetails)
      .build();

    var id = "123";

    stubFor(get(urlEqualTo(RESOURCE_PATH + "/" + id)).willReturn(aResponse()
      .withStatus(HttpStatus.NOT_FOUND.value())
      .withHeader(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_XML_VALUE)
      .withBody(xmlMapper.writeValueAsString(error))));

    assertThatThrownBy(() -> client.get(id))
      .isInstanceOf(FeignClientException.class)
      .asInstanceOf(InstanceOfAssertFactories.type(FeignClientException.class))
      .extracting(FeignClientException::getError)
      .isEqualTo(error);
  }

  @Test
  @SneakyThrows
  public void get_by_id_when_id_exists_then_return_business_data() {
    var errorMessage = "some error";
    var errorDetails = "some details";

    var business = createBusinessResponse(0, normalWeekHours());

    var id = "123";

    stubFor(get(urlEqualTo(RESOURCE_PATH + "/" + id)).willReturn(aResponse()
      .withStatus(HttpStatus.OK.value())
      .withHeader(HttpHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON_VALUE)
      .withBody(objectMapper.writeValueAsString(business))));

    var response = client.get(id);

    assertThat(response).isEqualTo(business);
  }
}
