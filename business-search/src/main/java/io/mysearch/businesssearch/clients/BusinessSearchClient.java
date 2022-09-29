package io.mysearch.businesssearch.clients;

import io.mysearch.businesssearch.models.BusinessResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "${feign.business-search.name}", url = "${feign.business-search.url}")
public interface BusinessSearchClient {

  @GetMapping("{id}")
  ResponseEntity<BusinessResponse> get(@PathVariable("id") String id);
}