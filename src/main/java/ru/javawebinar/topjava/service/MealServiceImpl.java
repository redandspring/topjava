package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.FilterDate;

import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFound;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNotFoundWithId;

@Service
public class MealServiceImpl implements MealService {

    @Autowired
    private MealRepository repository;

    @Override
    public List<Meal> getAll(int userId)
    {
        return repository.getAll(userId);
    }

    @Override
    public Meal get(int id, int userId)
    {
        return checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public List<Meal> getByDate(FilterDate filter, int userID)
    {
        return checkNotFound(repository.getByDate(filter, userID), "filter=" + filter);
    }

    @Override
    public Meal save(Meal meal)
    {
        return repository.save(meal);
    }

    @Override
    public void delete(int id, int userID)
    {
        if (get(id, userID) != null){
            checkNotFoundWithId(repository.delete(id), id);
        }
    }

    @Override
    public void update(Meal meal, int userID)
    {
        if (get(meal.getId(), userID) != null ){
            meal.setUserId(userID);
            repository.save(meal);
        }
    }

}