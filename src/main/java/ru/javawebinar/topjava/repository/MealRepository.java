package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.FilterDate;

import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(Meal Meal);

    boolean delete(int id);

    Meal get(int id, int userID);

    List<Meal> getAll(int userID);

    List<Meal> getByDate(FilterDate filter, int userID);
}
