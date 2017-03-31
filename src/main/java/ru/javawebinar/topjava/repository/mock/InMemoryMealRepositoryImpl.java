package ru.javawebinar.topjava.repository.mock;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.FilterDate;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

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
    public boolean delete(int id)
    {
        return repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, int userID) {
        return check(id, userID) ? repository.get(id) : null;
    }

    @Override
    public List<Meal> getAll(int userID) {
        return getByDate(new FilterDate(), userID);
    }

    @Override
    public List<Meal> getByDate(FilterDate filter, int userID)
    {
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == userID)
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), filter.getStartDate(), filter.getEndDate()))
                .filter(meal -> DateTimeUtil.isBetween(meal.getTime(), filter.getStartTime(), filter.getEndTime()))
                .sorted(Comparator.comparing(Meal::getDateTime))
                .collect(Collectors.toList());
    }

    private boolean check(int id, int userID){
        return (repository.containsKey(id) && repository.get(id).getUserId() == userID);
    }
}

