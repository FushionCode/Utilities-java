package com.fushioncoder.Utility_Service.utilities;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Slf4j
public class ListUtils {

    /**
     * Splits a list into smaller sublists. The sublists are of type ArrayList.
     * The original list remains unmodified and changes on the sublists are not propagated to the original list.
     *
     *
     * @param original
     *            The list to split
     * @param maxListSize
     *            The max amount of element a sublist can hold.
     * @return A list of sublists
     */
    public static final <T> List<List<T>> split(final List<T> original, final int maxListSize) {
        return split(original, maxListSize, ArrayList.class);
    }

    /** @param original
     *            The list to split
     * @param maxListSize
     *            The max amount of element a sublist can hold.
     * @param listImplementation
     *            The implementation of List to be used to create the returned sublists
     * @return A list of sublists
     * @throws IllegalArgumentException
     *             if the argument maxListSize is zero or a negative number
     * @throws NullPointerException
     *             if arguments original or listImplementation are null
     */
    public static <T> List<List<T>> split(final List<T> original, final int maxListSize,
                                          final Class<? extends List> listImplementation) {
        if (maxListSize <= 0) {
            throw new IllegalArgumentException("maxListSize must be greater than zero");
        }

        final T[] elements = (T[]) original.toArray();
        final int maxChunks = (int) Math.ceil(elements.length / (double) maxListSize);

        final List<List<T>> lists = new ArrayList<List<T>>(maxChunks);
        for (int i = 0; i < maxChunks; i++) {
            final int from = i * maxListSize;
            final int to = Math.min(from + maxListSize, elements.length);
            final T[] range = Arrays.copyOfRange(elements, from, to);

            lists.add(createSublist(range, listImplementation));
        }

        return lists;
    }

    private static <T> List<T> createSublist(final T[] elements, final Class<? extends List> listImplementation) {
        List<T> sublist;
        final List<T> asList = Arrays.asList(elements);
        try {
            sublist = listImplementation.newInstance();
            sublist.addAll(asList);
        } catch (final InstantiationException e) {
            sublist = asList;
        } catch (final IllegalAccessException e) {
            sublist = asList;
        }

        return sublist;
    }


    /**
     * ensure you pass groupBy as a Capitalised First-letter word
     * i.e. groupListBy(dummyList,  "GroupBy")
     *
     * @param listOfValues
     * @param groupBy
     * @param <T>
     * @return
     */
    public static <T> Map<Object, List<T>> groupListBy(List<T> listOfValues, String groupBy) {
        return listOfValues.stream().collect(Collectors.groupingBy(m -> {
            try {
                return String.valueOf(m.getClass().getMethod("get".concat(groupBy)).invoke(m));
            } catch (Exception ignored) {
                log.debug("ERROR WHILE REFLECTING FIELDS:::");
            }
            return groupBy;
        }));
    }

    public static <T> Map<Boolean, List<T>> partitionBasedOnCondition
            (List<T> inputList, Predicate<T> condition) {
        return inputList.stream().collect(Collectors.partitioningBy(condition));
    }

}
