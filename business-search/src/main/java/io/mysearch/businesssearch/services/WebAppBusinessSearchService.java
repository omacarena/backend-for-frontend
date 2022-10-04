package io.mysearch.businesssearch.services;

import io.mysearch.businesssearch.models.WebAppBusinessResponse;

public interface WebAppBusinessSearchService {
  WebAppBusinessResponse search(String id);
}
