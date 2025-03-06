package org.defix.vue.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VueController {
    @GetMapping({"/services/**", "/main"})
    public String services() { return "services"; }
}
