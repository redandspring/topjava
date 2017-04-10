package ru.javawebinar.topjava.service;

import static ru.javawebinar.topjava.MealTestData.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class MealServiceTest
{
    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        Meal meal = service.get(MEAL_LUNCH_ID, UserTestData.USER_ID);
        MATCHER.assertEquals(meal, MEAL_LUNCH);
    }

    @Test(expected = NotFoundException.class)
    public void getNonOwner() throws Exception{
        service.get(MEAL_LUNCH_ID, UserTestData.ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_LUNCH_ID, UserTestData.USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL_DINNER,MEAL_BREAKFAST), service.getAll(UserTestData.USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNonOwner() throws Exception{
        service.delete(MEAL_LUNCH_ID, UserTestData.ADMIN_ID);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> meals = service.getBetweenDateTimes(MEAL_LUNCH_DATE_TIME.minusMinutes(1), MEAL_LUNCH_DATE_TIME, UserTestData.USER_ID);
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL_LUNCH), meals);
    }

    @Test
    public void getAll() throws Exception {
        Collection<Meal> all = service.getAll(UserTestData.USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL_DINNER, MEAL_LUNCH, MEAL_BREAKFAST), all);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL_LUNCH);
        updated.setDescription("UpdateDescription");
        updated.setCalories(555);
        service.update(updated, UserTestData.USER_ID);
        MATCHER.assertEquals(updated, service.get(MEAL_LUNCH_ID, UserTestData.USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void updateNonOwner() throws Exception{
        Meal updated = new Meal(MEAL_LUNCH);
        updated.setDescription("UpdateDescription");
        updated.setCalories(555);
        service.update(updated, UserTestData.ADMIN_ID);
    }

    @Test
    public void save() throws Exception {
        Meal newMeal = new Meal(null, MEAL_LUNCH_DATE_TIME.plusDays(1), "saveTest", 555);
        Meal created = service.save(newMeal, UserTestData.USER_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(newMeal, MEAL_DINNER, MEAL_LUNCH, MEAL_BREAKFAST), service.getAll(UserTestData.USER_ID));
    }
}