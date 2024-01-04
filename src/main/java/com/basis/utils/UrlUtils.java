package com.basis.utils;

import com.basis.exception.BidEvaluatorException;

import java.net.URI;
import java.net.URISyntaxException;

public final class UrlUtils {

    private UrlUtils() {
    }

    public static String getHostFromUrl(String url) {
        try {
            return new URI(url).getHost();
        } catch (URISyntaxException ex) {
            throw new BidEvaluatorException("Could not parse url '%s' for domain extraction", ex);
        }
    }
}
