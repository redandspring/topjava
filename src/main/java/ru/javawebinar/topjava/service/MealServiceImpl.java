package ru.javawebinar.topjava.service;


import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoMemoryImpl;
import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public class MealServiceImpl implements MealService
{
    private MealDao dao = new MealDaoMemoryImpl();

    @Override
    public List<Meal> getAll()
    {
        return dao.findAll();
    }

    @Override
    public Meal getMeal(int id)
    {
        return dao.findAllById(id);
    }

    @Override
    public void addOrEditMeal(Meal meal)
    {
        if (meal.getId() > 0)
        {
            dao.update(meal);
        }
        else
        {
            dao.insert(meal);
        }
    }

    @Override
    public void delete(int id)
    {
        Meal meal = getMeal(id);
        if (meal != null)
        {
            dao.delete(meal);
        }
    }
}
