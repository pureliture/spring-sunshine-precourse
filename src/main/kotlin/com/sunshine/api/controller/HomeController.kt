package com.sunshine.api.controller

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController {
    @GetMapping("/")
    fun index(model: Model): String {
        model.addAttribute("title", "날씨 조회")
        return "index"
    }
}
