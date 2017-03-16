package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
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
        userMealWithExceeds.stream().forEach(System.out::println);

        // реализация через циклы
        System.out.println();
        System.out.println("Реализация через циклы:");
        List<UserMealWithExceed> userMealWithExceeds_FOREACH = getFilteredWithExceededOLD(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000);
        userMealWithExceeds_FOREACH.stream().forEach(System.out::println);

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

        List<UserMealWithExceed> result = mealList
                .stream()
                .filter((userMeal) -> TimeUtil.isBetween(userMeal.getDateTime().toLocalTime(), startTime, endTime))
                .map((userMeal) -> new UserMealWithExceed(
                        userMeal.getDateTime(),
                        userMeal.getDescription(),
                        userMeal.getCalories(),
                        (dayMap.get(userMeal.getDateTime().toLocalDate()) > caloriesPerDay)
                ))
                .collect(Collectors.toList());

        return result;
    }

    /**
     * like getFilteredWithExceeded
     * реализация через циклы
     */
    public static List<UserMealWithExceed> getFilteredWithExceededOLD(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay)
    {

        Map<LocalDate, Integer> dayMap = new HashMap<>();
        for (UserMeal um : mealList)
        {
            LocalDate localDate = um.getDateTime().toLocalDate();
            dayMap.put(localDate, dayMap.getOrDefault(localDate, 0) + um.getCalories());
        }

        List<UserMealWithExceed> result = new ArrayList<>();
        for (UserMeal um : mealList)
        {
            if (!TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime)) continue;

            Boolean exceed = (dayMap.get(um.getDateTime().toLocalDate()) > caloriesPerDay);
            result.add(new UserMealWithExceed(
                    um.getDateTime(),
                    um.getDescription(),
                    um.getCalories(),
                    exceed
            ));
        }
        return result;
    }
}
