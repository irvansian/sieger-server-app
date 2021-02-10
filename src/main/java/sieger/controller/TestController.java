package sieger.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class TestController {

    @Resource
    private HttpServletRequest request;
    
    

   
    @PostMapping(value = "/deleteById")
    public String deleteById(String id) {
        
        return "success";
    }
}