package io.mysearch.businesssearch.services;

import io.mysearch.businesssearch.clients.BusinessSearchClient;
import io.mysearch.businesssearch.clients.FeignClientError;
import io.mysearch.businesssearch.clients.FeignClientErrorCode;
import io.mysearch.businesssearch.clients.FeignClientException;
import io.mysearch.businesssearch.errors.BusinessNotFoundException;
import io.mysearch.businesssearch.fixtures.BusinessSearchFixture;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class WebAppBusinessSearchServiceTests {

  private static final String BUSINESS_ID = "12345";

  @Mock
  BusinessSearchClient client;

  @InjectMocks
  WebAppBusinessSearchServiceImpl service;

  BusinessSearchFixture fixture;

  @BeforeEach
  public void beforeEach() {
    fixture = new BusinessSearchFixture(client, service);
  }

  @Test
  public void search_when_id_null_then_throw_exception() {
    assertThatThrownBy(() -> service.search(null)).isExactlyInstanceOf(NullPointerException.class);
  }

  @Test
  public void search_when_id_empty_then_throw_exception() {
    assertThatThrownBy(() -> service.search("")).isExactlyInstanceOf(IllegalArgumentException.class);
  }

  @Test
  public void search_when_id_does_not_exist_then_throw_exception() {
    var id = "123";

    var error = FeignClientError.builder()
      .code(FeignClientErrorCode.NO_SUCH_KEY)
      .details("some details")
      .message("some message")
      .build();

    when(client.get(id)).thenThrow(new FeignClientException(error));

    assertThatThrownBy(() -> service.search(id)).isExactlyInstanceOf(BusinessNotFoundException.class);
  }

  @Test
  public void search_when_id_exist_and_no_opening_hours_then_return_correctly_mapped_business() {
    fixture.setupClientReturnsBusinessWithNoHours(BUSINESS_ID);

    fixture.assertThatServiceReturnsBusinessWithOnlyClosedHours(BUSINESS_ID);
  }

  @Test
  public void search_when_id_exist_and_only_closed_opening_hours_then_return_correctly_mapped_business() {
    fixture.setupClientReturnsBusinessWithOnlyClosedHours(BUSINESS_ID);

    fixture.assertThatServiceReturnsBusinessWithOnlyClosedHours(BUSINESS_ID);
  }

  @Test
  public void search_when_id_exist_and_normal_opening_hours_then_return_correctly_mapped_business() {
    fixture.setupClientReturnsBusinessWithNormalHours(BUSINESS_ID);

    fixture.assertThatServiceReturnsBusinessWithNormalHours(BUSINESS_ID);
  }
}
