package io.mysearch.businesssearch.fixtures;

import io.mysearch.businesssearch.clients.BusinessSearchClient;
import io.mysearch.businesssearch.services.WebAppBusinessSearchService;
import lombok.RequiredArgsConstructor;

import static io.mysearch.businesssearch.utils.BusinessResponseFactory.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@RequiredArgsConstructor
public class BusinessSearchFixture {

  private final BusinessSearchClient client;

  private final WebAppBusinessSearchService service;

  public void setupClientReturnsBusinessWithNoHours(String id) {
    when(this.client.get(id)).thenReturn(createBusinessResponse(0, noHours()));
  }

  public void assertThatServiceReturnsBusinessWithOnlyClosedHours(String id) {
    var response = this.service.search(id);

    var expectedResponse = createWebAppBusinessResponse(0, webAppOnlyClosedHours());

    assertThat(response).isEqualTo(expectedResponse);
  }

  public void setupClientReturnsBusinessWithOnlyClosedHours(String id) {
    when(this.client.get(id)).thenReturn(createBusinessResponse(0, onlyClosedHours()));
  }

  public void setupClientReturnsBusinessWithNormalHours(String id) {
    when(this.client.get(id)).thenReturn(createBusinessResponse(0, normalWeekHours()));
  }

  public void assertThatServiceReturnsBusinessWithNormalHours(String id) {
    var response = this.service.search(id);

    var expectedResponse = createWebAppBusinessResponse(0, webAppNormalWeekHours());

    assertThat(response).isEqualTo(expectedResponse);
  }
}
