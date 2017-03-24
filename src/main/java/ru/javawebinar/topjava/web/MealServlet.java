package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealNull;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.service.MealServiceImpl;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

public class MealServlet extends HttpServlet
{
    private static final Logger LOG = LoggerFactory.getLogger(MealServlet.class);

    private MealService service = new MealServiceImpl();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {

        String action = req.getParameter("action");
        int id = parseId(req.getParameter("id"));

        if ("EDIT".equals(action))
        {
            Meal meal = (id > 0) ? service.getMeal(id) : new MealNull();
            req.setAttribute("meal", meal);
            LOG.debug("show form");
            req.getRequestDispatcher("mealForm.jsp").forward(req, resp);
        }
        else if ("DELETE".equals(action) && id > 0)
        {
            service.delete(id);

            LOG.debug("meal deleted id = " + id);
            resp.sendRedirect("meals");
        }
        else
        {
            List<MealWithExceed> mealWithExceeds = MealsUtil.getMealWithExceeded(service.getAll(), 2000);
            req.setAttribute("meals", mealWithExceeds);
            LOG.debug("get List count= " + mealWithExceeds.size());
            req.getRequestDispatcher("meals.jsp").forward(req, resp);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException
    {
        req.setCharacterEncoding("UTF-8");
        int id = parseId(req.getParameter("id"));
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = parseId(req.getParameter("calories"));

        service.addOrEditMeal(new Meal(id, dateTime, description, calories));

        LOG.debug("meals refreshed");
        resp.sendRedirect("meals");
    }

    private int parseId(final String id)
    {
        try
        {
            return Integer.parseInt(id);
        }
        catch (NumberFormatException e)
        {
            return 0;
        }
    }
}
