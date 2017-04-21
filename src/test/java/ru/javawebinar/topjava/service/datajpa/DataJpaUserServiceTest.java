package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserServiceTest;

import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.DATAJPA})
public class DataJpaUserServiceTest extends UserServiceTest
{
    @Test
    public void testGetWithMeal() throws Exception {

        User user = service.getWithMeal(USER_ID);
        MATCHER.assertEquals(USER, user);
        MealTestData.MATCHER.assertEquals(MealTestData.MEAL1, user.getMeals().get(0));
    }
}
