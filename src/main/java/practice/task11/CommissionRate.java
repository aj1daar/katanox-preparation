package practice.task11;

import java.math.BigDecimal;

/** @param ratePercent e.g. {@code 15.00} means 15 % */
public record CommissionRate(String channel, BigDecimal ratePercent) {}
