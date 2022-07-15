package com.urise.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainMethods {
    public static void main(String[] args) {
        int[] values = {9, 8, 7, 9, 5};
        System.out.println(minValue(values));

        List<Integer> integers = new ArrayList<>(Arrays.asList(4, 2, 3));
        System.out.println(oddOrEven(integers));
    }

    private static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (s1, s2) -> s1 * 10 + s2);
    }

    private static List<Integer> oddOrEven(List<Integer> integers) {
        return integers.stream().filter(s -> s % 2 != integers.stream().reduce(Integer::sum).orElse(0) % 2).collect(Collectors.toList());
    }
}