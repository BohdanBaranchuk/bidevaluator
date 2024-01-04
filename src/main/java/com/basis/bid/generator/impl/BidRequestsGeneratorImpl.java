package com.basis.bid.generator.impl;

import com.basis.bid.generator.api.BidRequestsGenerator;
import com.basis.bid.model.BidRequest;
import com.basis.config.model.BidGeneratorConfiguration;
import com.basis.config.model.DimensionConfiguration;
import com.basis.config.model.UrlConfiguration;
import com.basis.exception.BidEvaluatorException;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.basis.utils.DimensionUtils.dimensionToString;

public class BidRequestsGeneratorImpl implements BidRequestsGenerator {

    private static final Random RANDOM = new Random();
    private static final int ONE_ITEM_DIMENSIONS_MAX_DEFAULT = 1;
    private static final int WEB_PORT = 80;

    @Override
    public List<BidRequest> generateFromConfiguration(BidGeneratorConfiguration configuration) {
        return IntStream.range(0, configuration.generatedItemsNumber())
                .mapToObj(seq -> generateBid(configuration))
                .toList();
    }

    private BidRequest generateBid(BidGeneratorConfiguration configuration) {

        var requestId = UUID.randomUUID().toString();
        var originCountry = generateItem(BidGeneratorConfiguration::countryCodes, configuration);
        var pageUrl = generateUrl(configuration.url());
        var placementDimensions = generateDimensions(configuration.dimension());

        return new BidRequest(requestId, originCountry, pageUrl, placementDimensions);
    }

    private String generateUrl(UrlConfiguration urlConfiguration) {

        var protocol = generateItem(UrlConfiguration::protocols, urlConfiguration);
        var host = generateItem(UrlConfiguration::hosts, urlConfiguration);
        var query = generateItem(UrlConfiguration::requestQueries, urlConfiguration);

        try {
            return new URI(protocol, null, host, WEB_PORT, query, null, null).toURL().toString();
        } catch (URISyntaxException | MalformedURLException ex) {
            var message = "Url could not be generated with protocol '%s' for domain '%s' with query string '%s'".formatted(protocol, host, query);
            throw new BidEvaluatorException(message, ex);
        }
    }

    private String generateDimensions(DimensionConfiguration dimension) {

        var oneItemDimensionsMax = Optional.ofNullable(dimension.maxSizesForOneItem())
                .orElse(ONE_ITEM_DIMENSIONS_MAX_DEFAULT);

        var itemDimensions = RANDOM.nextInt(oneItemDimensionsMax);

        return IntStream.rangeClosed(0, itemDimensions)
                .mapToObj(seq -> generateDimension(dimension))
                .collect(Collectors.joining(", "));
    }

    private String generateDimension(DimensionConfiguration dimension) {
        var dimensionConfiguration = generateItem(DimensionConfiguration::sizes, dimension);
        return dimensionToString(dimensionConfiguration);
    }

    private <T, R> R generateItem(Function<T, List<R>> extractItemsValues, T configuration) {
        var index = RANDOM.nextInt(extractItemsValues.apply(configuration).size());
        return extractItemsValues.apply(configuration).get(index);
    }
}
