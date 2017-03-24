package ru.javawebinar.topjava.web;

import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet
{
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        LOG.debug("get Meal Servlet");

        List<MealWithExceed> mealWithExceeds = MealsUtil.getMealWithExceeded(MealsUtil.dataMeal(), 2000);
        req.setAttribute("meals", mealWithExceeds);
        req.setAttribute("temp", mealWithExceeds.get(0));

        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }
}
