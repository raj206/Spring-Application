package hello;

import org.apache.commons.text.StringEscapeUtils;
import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import static org.jooq.codegen.maven.example.Tables.*;
import org.jooq.codegen.maven.example.tables.Springtest;
import org.jooq.impl.DSL;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;

@SpringBootApplication
public class Application {
    public static final Logger logger = LoggerFactory.getLogger(Application.class);

    public Application() throws SQLException {
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        logger.info("\u001B[34m" + "This is the logger speaking!" + "\u001B[0m");
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String myPara = "\u001B[35m" +"<p>This is my first para in Spring!</p>" + "\u001B[0m";

            System.out.println(StringEscapeUtils.escapeHtml4(myPara));

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }

        };
    }

    String userName = "postgres";
    String password = "";
    String url = "jdbc:postgresql://localhost:5432/postgres";

    // Connection is the only JDBC resource that we need
    // PreparedStatement and ResultSet are handled by jOOQ, internally
    public int jooqRunner() {
        try (Connection conn = DriverManager.getConnection(url, userName, password)) {
            System.out.println("Jooq's working");
            DSLContext create = DSL.using(conn, SQLDialect.POSTGRES);
            Result<Record> result;
            result = create.select().from(SPRINGTEST).fetch();
            for (Record r : result) {
                int pk = r.getValue(SPRINGTEST.PK);
                int cost = r.getValue(Springtest.SPRINGTEST.COST);
                String name = r.getValue(Springtest.SPRINGTEST.NAME);

                System.out.println("\u001B[35m" + "Primary Key: " + pk + " cost: " + cost + " name: " + name);
            }
        }

        // For the sake of this tutorial, let's keep exception handling simple
        catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }
    int a = jooqRunner();

}
