package com.app;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Comparator;
import java.util.List;

import static java.util.Collections.max;
import static java.util.Collections.min;

public class StatisticsCollector {

    private final List<BigInteger> bigIntegersList;
    private final List<BigDecimal> bigDecimalList;
    private final List<String> stringList;

    public StatisticsCollector(List<BigInteger> bigIntegersList, List<BigDecimal> bigDecimalList, List<String> stringList) {
        this.bigIntegersList = bigIntegersList;
        this.bigDecimalList = bigDecimalList;
        this.stringList = stringList;
    }

    public void collectStatistics() {
        if (!bigIntegersList.isEmpty())
            collectIntegerStatistics();
        if (!bigDecimalList.isEmpty())
            collectFloatStatistics();
        if (!stringList.isEmpty())
            collectStringStatistics();
    }

    private void collectStringStatistics() {
        String longest = stringList.stream().
                max(Comparator.comparingInt(String::length)).get();
        String shortest = stringList.stream().
                min(Comparator.comparingInt(String::length)).get();
        System.out.println("Longest string: " + longest);
        System.out.println("Shortest string: " + shortest);
    }

    private void collectFloatStatistics() {
        BigDecimal sum = BigDecimal.ZERO;
        BigDecimal max = max(bigDecimalList);
        BigDecimal min = min(bigDecimalList);
        for (BigDecimal d : bigDecimalList) {
            sum = sum.add(d);
        }
        BigDecimal mean = sum.divide(BigDecimal.valueOf(bigDecimalList.size()));
        System.out.println("Max of floats: " + max);
        System.out.println("Min of floats: " + min);
        System.out.println("Sum of floats: " + sum);
        System.out.println("Mean of floats: " + mean);
    }

    private void collectIntegerStatistics() {
        BigInteger sum = BigInteger.ZERO;
        BigInteger max = max(bigIntegersList);
        BigInteger min = min(bigIntegersList);
        for (BigInteger bi : bigIntegersList) {
            sum = sum.add(bi);
        }
        BigInteger mean = sum.divide(BigInteger.valueOf(bigIntegersList.size()));
        System.out.println("Max of integers: " + max);
        System.out.println("Min of integers: " + min);
        System.out.println("Sum of integers: " + sum);
        System.out.println("Mean of integers: " + mean);
    }
}
