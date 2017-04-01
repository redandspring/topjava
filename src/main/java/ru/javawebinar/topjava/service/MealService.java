package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealService {
    List<Meal> getAll(int userId);

    Meal get(int id, int userId);

    List<Meal> getByDate(LocalDate startDate, LocalDate endDate, int userID);

    Meal save(Meal meal);

    void delete(int id, int userID);

    void update(Meal meal, int userID);

}