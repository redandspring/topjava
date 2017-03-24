package ru.javawebinar.topjava.model;

import java.time.LocalDateTime;

public class MealNull extends Meal
{
    private static int id = 0;
    private static LocalDateTime dateTime = LocalDateTime.now();
    private static String description = "";
    private static int calories = 0;

    public MealNull()
    {
        super(id, dateTime, description, calories);
    }
}
