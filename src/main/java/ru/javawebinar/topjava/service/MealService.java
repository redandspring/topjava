package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.FilterDate;

import java.util.List;

public interface MealService {
    List<Meal> getAll(int userId);

    Meal get(int id, int userId);

    List<Meal> getByDate(FilterDate filter, int userID);

    Meal save(Meal meal);

    void delete(int id, int userID);

    void update(Meal meal, int userID);

}