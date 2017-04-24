package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

@ActiveProfiles({Profiles.ACTIVE_DB, Profiles.JPA})
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management

        try (AnnotationConfigApplicationContext ctx = new AnnotationConfigApplicationContext()){

            ctx.getEnvironment().setActiveProfiles(Profiles.ACTIVE_DB, Profiles.REPOSITORY_IMPLEMENTATION);
            ctx.refresh();

            try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext(
                    new String[] {"spring/spring-app.xml", "spring/spring-db.xml"}, ctx)) {

                System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
                AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
                adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));
                System.out.println();

                MealRestController mealController = appCtx.getBean(MealRestController.class);
                List<MealWithExceed> filteredMealsWithExceeded =
                        mealController.getBetween(
                                LocalDate.of(2015, Month.MAY, 30), LocalTime.of(7, 0),
                                LocalDate.of(2015, Month.MAY, 31), LocalTime.of(11, 0));
                filteredMealsWithExceeded.forEach(System.out::println);
            }
        }
    }
}
