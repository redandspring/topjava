package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );
        List<UserMealWithExceed> userMealWithExceeds = getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        userMealWithExceeds.forEach(System.out::println);

        // реализация через циклы
        System.out.println();
        System.out.println("Реализация через циклы:");
        List<UserMealWithExceed> userMealWithExceedsOldVersion = getFilteredWithExceededOldVersion(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        userMealWithExceedsOldVersion.forEach(System.out::println);

    }


    public static List<UserMealWithExceed> getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay)
    {

        final Map<LocalDate, Integer> dayMap = mealList
                .stream()
                .collect(
                        Collectors.groupingBy(
                                userMeal -> userMeal.getDateTime().toLocalDate(),
                                Collectors.summingInt(UserMeal::getCalories)
                        )
                );

        return mealList
                .stream()
                .filter(userMeal -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map(userMeal -> new UserMealWithExceed(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        (dayMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)
                ))
                .collect(Collectors.toList());
    }

    /**
     * like getFilteredWithExceeded
     * реализация через циклы
     */
    public static List<UserMealWithExceed> getFilteredWithExceededOldVersion(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay)
    {

        Map<LocalDate, Integer> dayMap = new HashMap<>();
        mealList.forEach(userMeal -> {
            LocalDate localDate = userMeal.getDateTime().toLocalDate();
            dayMap.put(localDate, dayMap.getOrDefault(localDate, 0) + userMeal.getCalories());
        });

        List<UserMealWithExceed> result = new ArrayList<>();
        mealList.forEach(userMeal -> {
            if (TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime)) {
                Boolean exceed = (dayMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay);
                result.add(new UserMealWithExceed(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        exceed
                ));
            }
        });
        return result;
    }
}
