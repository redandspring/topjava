package ru.javawebinar.topjava.web.meal;


import static java.time.LocalDateTime.of;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.web.meal.MealRestController.REST_URL;
import static ru.javawebinar.topjava.MealTestData.*;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import ru.javawebinar.topjava.TestUtil;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

public class MealRestControllerTest extends AbstractControllerTest {

    @Test
    public void testGet() throws Exception {
        mockMvc.perform(get(REST_URL + MEAL1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                // https://jira.spring.io/browse/SPR-14472
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER.contentMatcher(MEAL1));
    }

    @Test
    public void testDelete() throws Exception {
        mockMvc.perform(delete(REST_URL + MEAL1_ID))
                .andDo(print())
                .andExpect(status().isOk());
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2), mealService.getAll(UserTestData.USER_ID));
    }

    @Test
    public void getAll() throws Exception {
        TestUtil.print(mockMvc.perform(get(REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(MealsUtil.getWithExceeded(MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY )))
                 );
    }

    @Test
    public void createRest() throws Exception {
        Meal expected = new Meal(null, of(2015, Month.MAY, 30, 10, 0), "New", 700);
        ResultActions action = mockMvc.perform(post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(expected))).andExpect(status().isCreated());

        Meal returned = MATCHER.fromJsonAction(action);
        expected.setId(returned.getId());

        MATCHER.assertEquals(expected, returned);
        MATCHER.assertEquals(expected, mealService.get(expected.getId(), UserTestData.USER_ID));
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MEAL1);
        updated.setDescription("UpdatedDescription");
        mockMvc.perform(put(REST_URL + MEAL1_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isOk());

        MATCHER.assertEquals(updated, mealService.get(MEAL1_ID, UserTestData.USER_ID));
    }

    @Test
    public void getBetween() throws Exception {

        final LocalDateTime start = LocalDateTime.parse("2015-05-30T09:05:30");
        final LocalDateTime end = LocalDateTime.parse("2015-05-30T11:15:30");

        mockMvc.perform(get(REST_URL + "filter?start=" + start + "&end=" + end ))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(MATCHER_WITH_EXCEED.contentListMatcher(MealsUtil.getFilteredWithExceeded(
                        Arrays.asList(MEAL1, MEAL2, MEAL3),
                        start.toLocalTime(),
                        end.toLocalTime(),
                        MealsUtil.DEFAULT_CALORIES_PER_DAY )));
    }

}