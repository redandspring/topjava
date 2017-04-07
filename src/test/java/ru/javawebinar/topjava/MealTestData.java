package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {

    public static final int MEAL_BREAKFAST_ID = START_SEQ + 2;
    public static final int MEAL_LUNCH_ID = START_SEQ + 3;
    public static final int MEAL_DINNER_ID = START_SEQ + 4;
    public static final LocalDateTime MEAL_LUNCH_DATE_TIME = LocalDateTime.of(2015, Month.MAY, 18, 12, 34);

    public static final Meal MEAL_BREAKFAST = new Meal(MEAL_BREAKFAST_ID, MEAL_LUNCH_DATE_TIME.minusHours(3), "Завтрак", 600);
    public static final Meal MEAL_LUNCH = new Meal(MEAL_LUNCH_ID, MEAL_LUNCH_DATE_TIME, "Обед", 520);
    public static final Meal MEAL_DINNER = new Meal(MEAL_DINNER_ID, MEAL_LUNCH_DATE_TIME.plusHours(7), "Ужин", 820);

    public static final ModelMatcher<Meal> MATCHER = new ModelMatcher<>();




}
