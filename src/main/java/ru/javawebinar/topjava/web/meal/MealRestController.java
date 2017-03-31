package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.FilterDate;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {

    private final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private MealService service;

    public List<MealWithExceed> getAll(){
        LOG.info("getAll");
        return MealsUtil.getWithExceeded(service.getAll(AuthorizedUser.id()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal get(int id){
        LOG.info("get " + id);
        return service.get(id, AuthorizedUser.id());
    }

    public List<MealWithExceed> getByDate(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime){
        FilterDate filter = new FilterDate(startDate, endDate, startTime, endTime);
        LOG.info("getByDate " + filter);
        return MealsUtil.getWithExceeded(service.getByDate(filter, AuthorizedUser.id()), MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public Meal create(Meal meal) {
        LOG.info("create " + meal);
        checkNew(meal);
        meal.setUserId(AuthorizedUser.id());
        return service.save(meal);
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal, int id) {
        LOG.info("update " + meal);
        checkIdConsistent(meal, id);
        service.update(meal, AuthorizedUser.id());
    }



}