package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

/**
 * GKislin
 * 06.03.2015.
 */
public interface MealRepository {
    Meal save(Meal meal);

    Meal update(Meal meal, int userID);

    Boolean delete(int id, int userID);

    Meal get(int id, int userID);

    List<Meal> getAll(int userID);

    List<Meal> getByDate(LocalDate startDate, LocalDate endDate, int userID);
}
