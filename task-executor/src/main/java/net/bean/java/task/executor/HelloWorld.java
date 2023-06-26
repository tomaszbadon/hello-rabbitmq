package net.bean.java.task.executor;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HelloWorld {

    public static void main(String[] args) {
        List<Integer> integerList = new ArrayList(List.of(12, 74, 32, 63, 45, 36));

        List<Integer> sorted = integerList.stream()
                .map(i -> Pair.of(i, String.valueOf(i)))
                .map(p -> Pair.of(p.getLeft(), Integer.parseInt(String.valueOf(p.getRight().charAt(0))) + Integer.parseInt(String.valueOf(p.getRight().charAt(1)))))
                .sorted(Comparator.comparingInt(Pair::getRight))
                .map(Pair::getLeft)
                .collect(Collectors.toList());

        System.out.println(sorted);

    }

}
