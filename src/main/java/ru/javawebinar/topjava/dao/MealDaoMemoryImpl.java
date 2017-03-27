package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class MealDaoMemoryImpl implements MealDao
{
    private final Map<Integer, Meal> storage = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.DATA.forEach(this::insert);
    }

    @Override
    public List<Meal> findAll()
    {
        return new ArrayList<>(storage.values());
    }

    @Override
    public Meal findById(int id)
    {
        return storage.get(id);
    }

    @Override
    public void insert(Meal meal)
    {
        int id  = counter.incrementAndGet();
        meal.setId(id);
        storage.put(id, meal);
    }

    @Override
    public void update(Meal meal)
    {
        storage.put(meal.getId(), meal);
    }

    @Override
    public void delete(int id)
    {
        storage.remove(id);
    }
}
