package io.mysearch.businesssearch.rest_controllers;

import io.mysearch.businesssearch.models.WebAppBusinessResponse;
import io.mysearch.businesssearch.services.WebAppBusinessSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.util.MimeTypeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Business search REST controller for client applications.
 */
@RestController
@RequestMapping(path = "/business")
@RequiredArgsConstructor
public class WebAppBusinessSearchController {

  private final WebAppBusinessSearchService searchService;

  @GetMapping(path = "{id}", produces = {MimeTypeUtils.APPLICATION_JSON_VALUE})
  public WebAppBusinessResponse get(@PathVariable("id") String id) {
    return searchService.search(id);
  }
}
