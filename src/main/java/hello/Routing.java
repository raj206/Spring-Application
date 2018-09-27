package hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Routing {
    @Controller
    class ThisWillActuallyRun {
        @RequestMapping("/raj")
        @ResponseBody
        public String home() {
            return "Hello World!";
        }
    }
}
