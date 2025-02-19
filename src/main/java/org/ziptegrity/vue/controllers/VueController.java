package org.ziptegrity.vue.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class VueController {
    @GetMapping({"/services/**", "/main"})
    public String services() { return "services"; }
}
