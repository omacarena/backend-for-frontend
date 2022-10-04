package io.mysearch.businesssearch.rest_controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.mysearch.businesssearch.errors.BusinessNotFoundException;
import io.mysearch.businesssearch.errors.ErrorCode;
import io.mysearch.businesssearch.models.WebAppBusinessResponse;
import io.mysearch.businesssearch.services.WebAppBusinessSearchService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static io.mysearch.businesssearch.utils.BusinessResponseFactory.createWebAppBusinessResponse;
import static io.mysearch.businesssearch.utils.BusinessResponseFactory.webAppNormalWeekHours;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class WebAppBusinessSearchControllerTests {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private WebAppBusinessSearchService service;

  @Test
  public void get_by_id_when_successful_then_return_response() throws Exception {
    var expectedResponse = createWebAppBusinessResponse(0, webAppNormalWeekHours());

    when(service.search(anyString())).thenReturn(expectedResponse);

    var responseJson = this.mockMvc.perform(MockMvcRequestBuilders.get("/business/123"))
      .andExpect(status().isOk())
      .andReturn().getResponse().getContentAsString();

    var response = objectMapper.readValue(responseJson, WebAppBusinessResponse.class);

    assertThat(response).isEqualTo(expectedResponse);
  }

  @Test
  public void get_by_id_when_id_not_found_then_return_error() throws Exception {
    var expectedResponse = createWebAppBusinessResponse(0, webAppNormalWeekHours());

    when(service.search(anyString())).thenThrow(new BusinessNotFoundException());

    this.mockMvc.perform(MockMvcRequestBuilders.get("/business/123"))
      .andExpect(status().isNotFound())
      .andExpect(jsonPath("$.code").value(ErrorCode.BUSINESS_NOT_FOUND.name()));
  }
}
