package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealDao
{
    List<Meal> findAll();
    Meal findAllById(int id);
    void insert(Meal meal);
    void update(Meal meal);
    void delete(Meal meal);
}
