package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealService
{
    List<Meal> getAll();
    Meal get(int id);
    void addOrEdit(Meal meal);
    void delete(int id);
}
