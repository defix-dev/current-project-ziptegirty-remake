package org.defix.vue.controllers;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.NoHandlerFoundException;

@Controller
public class ViewRedirectController implements ErrorController {
    @GetMapping("/error")
    public String handleError(HttpServletRequest req) {
        Integer statusCode = (Integer) req.getAttribute("jakarta.servlet.error.status_code");

        if (statusCode != null && statusCode == HttpStatus.NOT_FOUND.value()) {
            return "redirect:/main";
        }

        return "error";
    }
}
