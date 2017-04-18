package ru.javawebinar.topjava.repository.jpa;

import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

@Repository
@Transactional(readOnly = true)
public class JpaMealRepositoryImpl implements MealRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        User userRef = em.getReference(User.class, userId);
        meal.setUser(userRef);
        if (meal.isNew()) {
            em.persist(meal);
            return meal;
        } else {
            Meal mealFind = em.find(Meal.class, meal.getId());
            return (mealFind != null && userId == mealFind.getUser().getId()) ? em.merge(meal) : null;
        }
    }

    @Override
    @Transactional
    public boolean delete(int id, int userId) {

        return em.createNamedQuery(Meal.DELETE)
                .setParameter("id", id)
                .setParameter("userId", userId)
                .executeUpdate() != 0;
    }

    @Override
    public Meal get(int id, int userId) {

        Meal meal = em.find(Meal.class, id);
        return (meal != null && userId == meal.getUser().getId()) ? meal : null;
    }

    @Override
    public List<Meal> getAll(int userId) {
        return em.createNamedQuery(Meal.ALL_SORTED, Meal.class)
                .setParameter("userId", userId)
                .getResultList();
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return em.createNamedQuery(Meal.GET_BETWEEN_DATE, Meal.class)
                .setParameter("userId", userId)
                .setParameter("start", startDate)
                .setParameter("end", endDate)
                .getResultList();
    }
}