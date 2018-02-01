package margincalculator;

import org.junit.Test;

import java.math.BigDecimal;

import static junit.framework.TestCase.assertTrue;

public class RevenueCalculatorTest {

    @Test
    public void calculateRevenueCost400Margin20Test() {
        double revenue = 500;
        double maginPercentage = 20;
        double cost = 400;
        BigDecimal bigDecimal = getBigDecimal(maginPercentage, cost);
        assertTrue("The revenue should be " + revenue, getBigDecimal(maginPercentage, cost).doubleValue() == revenue);
    }

    @Test
    public void calculateRevenueCost800Margin15Test() {
        double revenue = 800;
        double maginPercentage = 15;
        double cost = 680;
        BigDecimal bigDecimal = getBigDecimal(maginPercentage, cost);
        assertTrue("The revenue should be " + revenue, getBigDecimal(maginPercentage, cost).doubleValue() == revenue);
    }

    @Test
    public void calculateRevenueCost800Margin15_5Test() {
        double revenue = 800;
        double maginPercentage = 15.5;
        double cost = 676;
        BigDecimal bigDecimal = getBigDecimal(maginPercentage, cost);
        assertTrue("The revenue should be " + revenue, getBigDecimal(maginPercentage, cost).doubleValue() == revenue);
    }

    @Test
    public void calculateRevenueCost800Margin0Test() {
        double revenue = 800;
        double maginPercentage = 0;
        double cost = 800;
        assertTrue("The revenue should be " + revenue, getBigDecimal(maginPercentage, cost).doubleValue() == revenue);
    }

    @Test
    public void calculateRevenueCost800MarginNegativeTest() {
        double revenue = 800;
        double maginPercentage = -10;
        double cost = 880;
        assertTrue("The revenue should be " + revenue, getBigDecimal(maginPercentage, cost).doubleValue() == revenue);
    }

    private BigDecimal getBigDecimal(double maginPercentage, double cost) {
        RevenueCalculator revenueCalculator = new RevenueCalculatorImpl();
        return revenueCalculator.calculateRevenue(new BigDecimal(maginPercentage), new BigDecimal(cost));
    }

}
