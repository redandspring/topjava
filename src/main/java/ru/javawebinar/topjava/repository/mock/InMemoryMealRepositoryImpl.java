package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
        }
        repository.put(meal.getId(), meal);
        return meal;
    }

    @Override
    public Meal update(Meal meal, int userId) {

        return get(meal.getId(), userId) != null ? save(meal) : null;
    }

    @Override
    public Boolean delete(int id, int userID)
    {
        return get(id, userID) != null ? repository.remove(id) != null : null;
    }

    @Override
    public Meal get(int id, int userId) {

        Meal meal = repository.getOrDefault(id, null);
        return (meal != null && meal.getUserId() == userId) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userID) {
        return getByDate(LocalDate.MIN, LocalDate.MAX, userID);
    }

    @Override
    public List<Meal> getByDate(LocalDate startDate, LocalDate endDate, int userID)
    {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userID)
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), startDate, endDate))
                .sorted(Comparator.comparing(Meal::getDateTime).reversed())
                .collect(Collectors.toList());
    }
}

