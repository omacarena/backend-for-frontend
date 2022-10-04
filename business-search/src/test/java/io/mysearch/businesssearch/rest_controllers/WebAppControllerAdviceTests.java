package io.mysearch.businesssearch.rest_controllers;

import io.mysearch.businesssearch.errors.BusinessNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class WebAppControllerAdviceTests {

  private WebAppControllerAdvice advice = new WebAppControllerAdvice();

  @Test
  public void handleBusinessNotFound_returns_correct_response() {
    var exception = new BusinessNotFoundException();

    var response = advice.handleBusinessNotFound(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).isEqualTo(exception);
  }
}
