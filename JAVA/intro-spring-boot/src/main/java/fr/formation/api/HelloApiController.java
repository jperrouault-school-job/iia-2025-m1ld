package fr.formation.api;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import fr.formation.exception.HelloException;

// @Controller
// @ResponseBody
@RestController
@RequestMapping("/hello") // Préfixer tous les mappings de la classe
public class HelloApiController {
    
    // @RequestMapping(path = "/", method = RequestMethod.GET)
    @GetMapping("/demo")
    public String hello(@RequestParam String username, @RequestParam(required = false) Integer age) {
        HelloRequest request = new HelloRequest();

        request.setUsername(username);
        request.setAge(age);

        return "Hello " + username;
    }

    // Data Binding avec paramètres de requête
    @GetMapping
    public HelloRequest hello(HelloRequest request) {
        return request;
        // return "Hello " + request.getUsername();
    }
    
    // Data Binding avec corps de requête
    @PostMapping
    @ResponseStatus(code = HttpStatus.CREATED)
    public String helloPost(@RequestBody HelloRequest request) {
        return "Hello " + request.getUsername();
    }

    @GetMapping({ "/hello2", "/hello2/{username}" })
    public String helloPV(@PathVariable(required = false) String username) {
        return "Hello " + username;
    }

    @GetMapping("/error")
    public ResponseEntity<String> error() {
        try {
            throw new RuntimeException();
            // return ResponseEntity.ok("Contenu");
        }

        catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @GetMapping("/error2")
    public String error2() {
        throw new HelloException();
    }
}
