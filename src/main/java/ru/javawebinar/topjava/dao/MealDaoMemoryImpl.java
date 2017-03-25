package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealDaoMemoryImpl implements MealDao
{
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.data().forEach(meal -> {
            int id  = counter.incrementAndGet();
            meal.setId(id);
            storage.putIfAbsent(id, meal);
        });
    }

    @Override
    public List<Meal> findAll()
    {
        return storage.entrySet().stream()
            .map(Map.Entry::getValue)
            .collect(Collectors.toList());
    }

    @Override
    public Meal findAllById(int id)
    {
        return storage.get(id);
    }

    @Override
    public void insert(Meal meal)
    {
        int id = counter.incrementAndGet();
        meal.setId(id);
        storage.putIfAbsent(id, meal);
    }

    @Override
    public void update(Meal meal)
    {
        storage.put(meal.getId(), meal);
    }

    @Override
    public void delete(Meal meal)
    {
        storage.remove(meal.getId(), meal);
    }
}
