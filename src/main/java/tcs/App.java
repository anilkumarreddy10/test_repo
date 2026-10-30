@RestController
public class AppController {

    @GetMapping("/")
    public String hello() {
        return "Hello good World!";
    }
}
