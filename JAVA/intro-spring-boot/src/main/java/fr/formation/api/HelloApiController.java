package fr.formation.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// @Controller
// @ResponseBody
@RestController
public class HelloApiController {
    
    // @RequestMapping(path = "/", method = RequestMethod.GET)
    @GetMapping
    public String hello(@RequestParam String username, @RequestParam(required = false) Integer age) {
        HelloRequest request = new HelloRequest();

        request.setUsername(username);
        request.setAge(age);

        return "Hello " + username;
    }

    // Data Binding
    @GetMapping("/hello")
    public HelloRequest hello(HelloRequest request) {
        return request;
        // return "Hello " + request.getUsername();
    }
}
