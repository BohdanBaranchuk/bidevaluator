# Welcome

## Bid Evaluator

Simplistic system for evaluating bid requests against advertising campaigns.

## Prerequisites

You need the following tools:

- Maven
- Java 21+
- Eclips or IntelliJ

## How to build

You might need the following steps to buit service:

```
  mvn clean install
```

## How to start

Run from main class
> com.basis.BidEvaluatorMain

## Tech solutions

1. The list of campaigns predefined in the `src/main/resources/campaign/campaign-list.json` file
2. The configuration to generate random bid requests defined in the `src/main/resources/bid/bid-generator-config.json`
   file
    1. Parameter `generatedItemsNumber` defines how many bid requests generate
    2. Parameter `dimension.maxSizesForOneItem` defines how many random dimensions could be mentioned in one dimensions
       row
3. Campaigns evaluated against generated bid requests using exact match by country, domain and dimensions
4. Evaluation aggregated result saved into project root folder with name in
   format `evaluation-results_2024-01-04T04:34:26.342834085.json`

## Tech doubts

1. Probably could be defined default campaigns if no exact matching found
2. Dimensions could be evaluated not only for exact matching but also by approximate size to return most similar
   dimensions
3. For each matching attribute could be defined weight value depending on how important this matching is
4. Could be created priority queue based on how frequently bid request searching for something to make this item
   firstly checked for matching

## Examples

### Bid request example

```
{
    "requestId": "550e8400-e29b-41d4-a716-44665544000",
    "originCountry": "CA",
    "pageUrl": "http://apple.com/ca/store?item=1290",
    "placementDimensions": "300x250, 100x200"
}
```

---

### Campaigns entity example

```
{
    "campaignId": "550e8400-e29b-41d4-a716-44665544000",
    "targetedCountry": "CA",
    "targetedDomain": "apple.com",
    "availableDimensions": "300x250, 600x200"
}
```

---

### Evaluated result example

```
{
    "definedCampaigns": [
        {
            "campaignId": "550e8400-e29b-41d4-a716-44665544000",
            "targetedCountry": "CA",
            "targetedDomain": "apple.com",
            "availableDimensions": "300x250, 600x200"
        },
        {
            "campaignId": "550e8400-e29b-41d4-a716-4466554er45",
            "targetedCountry": "US",
            "targetedDomain": "microsoft.com",
            "availableDimensions": "7600x200"
        }
    ],
    "evaluationResults": [
        {
            "bidRequest": {
                "requestId": "550e8400-e29b-41d4-a716-44665544000",
                "originCountry": "CA",
                "pageUrl": "http://apple.com/ca/store?item=1290",
                "placementDimensions": "300x250, 100x200"
            },
            "eligibleCampaignIds": [
                "550e8400-e29b-41d4-a716-44665544000",
                "550e8400-e29b-41d4-a716-4466554er45"
            ]
        }
    ],
    "totalBidRequests": 50000,
    "evaluationTime": "10 ms"
}
```

