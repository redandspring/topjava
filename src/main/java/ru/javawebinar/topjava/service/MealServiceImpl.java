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
    public Meal get(int id)
    {
        return dao.findById(id);
    }

    @Override
    public void addOrEdit(Meal meal)
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
        dao.delete(id);
    }
}
