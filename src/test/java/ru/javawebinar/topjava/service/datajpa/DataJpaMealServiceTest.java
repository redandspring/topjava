package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealServiceTest;

import static ru.javawebinar.topjava.MealTestData.*;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaMealServiceTest extends MealServiceTest
{
    @Test
    public void testGetWithUser() throws Exception {
        Meal actual = service.getWithUser(ADMIN_MEAL_ID, UserTestData.ADMIN_ID);
        MATCHER.assertEquals(ADMIN_MEAL1, actual);
        UserTestData.MATCHER.assertEquals(UserTestData.ADMIN, actual.getUser());
    }
}
