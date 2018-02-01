package margincalculator;

import java.math.BigDecimal;

public class RevenueCalculatorImpl implements RevenueCalculator {

    public BigDecimal calculateRevenue(BigDecimal marginPercentage, BigDecimal costOfGoods) {
        BigDecimal denominator = new BigDecimal(1).subtract(marginPercentage.divide(new BigDecimal(100)));
        return costOfGoods.divide(denominator);
    }
}
