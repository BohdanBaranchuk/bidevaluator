package com.basis.config.model;

import java.util.List;

public record UrlConfiguration(List<String> protocols, List<String> hosts, List<String> requestQueries) {
}
