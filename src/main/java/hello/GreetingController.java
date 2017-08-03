package hello;

import java.util.concurrent.atomic.AtomicLong;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    ValidateIDToken validateIDToken=new ValidateIDToken();
    @RequestMapping(value="/greeting",method = RequestMethod.POST)
    public Greeting greeting(@RequestParam(value="idToken", defaultValue="World") String id) {
        System.out.println(id);
        //validateIDToken.sendRequest(id);
        validateIDToken.validateUsingGoogleAPI(id);

        return new Greeting(counter.incrementAndGet(),
                            String.format(template, id));
    }
}
