package com.airtribe.smart_parking_lot.strategy;

import com.airtribe.smart_parking_lot.enums.FeeStrategy;
import org.springframework.stereotype.Component;

@Component
public class FeeCalculationStrategyFactory {

    private final DiscountedFeeCalculationStrategy discountedFeeCalculationStrategy;
    private final NormalFeeCalculationStrategy normalFeeCalculationStrategy;

    public FeeCalculationStrategyFactory(DiscountedFeeCalculationStrategy discountedFeeCalculationStrategy,
                                         NormalFeeCalculationStrategy normalFeeCalculationStrategy) {
        this.discountedFeeCalculationStrategy = discountedFeeCalculationStrategy;
        this.normalFeeCalculationStrategy = normalFeeCalculationStrategy;
    }

    public FeeCalculationStrategy identifyCalculationStrategy(FeeStrategy feeStrategy) {
        return switch (feeStrategy) {
            case DISCOUNTED -> discountedFeeCalculationStrategy;
            case NORMAL -> normalFeeCalculationStrategy;
        };
    }

}
