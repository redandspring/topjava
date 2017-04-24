package ru.javawebinar.topjava.service;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;

import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
abstract public class ServiceTest {
    protected static final Logger LOG = LoggerFactory.getLogger(ServiceTest.class);


    private static StringBuilder results = new StringBuilder();

    @Autowired
    private ApplicationContext applicationContext;
    private static String[] profiles;
    private static String classImpl;

    static {
        // needed only for java.util.logging (postgres driver)
        SLF4JBridgeHandler.install();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result).append('\n');
            LOG.info(result + " ms\n");
        }
    };

    @BeforeClass
    public static void setUpClass()
    {
        results = new StringBuilder();
    }

    @After
    public void setActiveProfiles(){
        profiles = applicationContext.getEnvironment().getActiveProfiles();
        classImpl = this.getClass().getSimpleName();
    }

    @AfterClass
    public static void printResult() {

        LOG.info("\nClass: " + classImpl +
                "\nActiveProfiles: " + Arrays.toString(profiles) +
                "\n---------------------------------" +
                "\nTest                 Duration, ms" +
                "\n---------------------------------\n" +
                results +
                "---------------------------------\n");
    }


}
