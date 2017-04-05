package ru.javawebinar.topjava.repository.mock;

import java.time.LocalDate;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.springframework.stereotype.Repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {

        Map<Integer, Meal> mealMap = repository.computeIfAbsent(meal.getUserId(), i -> new ConcurrentHashMap<>());

        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        else if ( ! mealMap.containsKey(meal.getId())) {
            return null;
        }

        return mealMap.put(meal.getId(), meal);
    }

    @Override
    public Boolean delete(int id, int userId)
    {
        Map<Integer, Meal> mealMap = repository.getOrDefault(userId, null);
        return mealMap != null && mealMap.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userId) {

        Map<Integer, Meal> mealMap = repository.getOrDefault(userId, null);
        return (mealMap == null) ? null : mealMap.getOrDefault(id, null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getByDate(LocalDate.MIN, LocalDate.MAX, userId);
    }

    @Override
    public List<Meal> getByDate(LocalDate startDate, LocalDate endDate, int userId)
    {
        Map<Integer, Meal> mealMap = repository.getOrDefault(userId, null);

        return (mealMap == null) ? Collections.EMPTY_LIST :
                mealMap.values().stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

