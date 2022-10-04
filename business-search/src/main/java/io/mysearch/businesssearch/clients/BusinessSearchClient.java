package io.mysearch.businesssearch.clients;

import io.mysearch.businesssearch.models.BusinessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Feign client for business search service.
 */
@FeignClient(
  name = "${feign.business-search.name}",
  url = "${feign.business-search.url}",
  configuration = BusinessSearchClientConfiguration.class
)
public interface BusinessSearchClient {
  @GetMapping(path = "{id}")
  BusinessResponse get(@PathVariable("id") String id);
}