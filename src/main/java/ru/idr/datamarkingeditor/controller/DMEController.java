package ru.idr.datamarkingeditor.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


@Controller
public class DMEController {

    @RequestMapping(value = {"/datamarkingeditor", "/datamarkingeditor/main"})
    public String pageMethod(Model model, HttpServletRequest request, HttpServletResponse response) {

        model.addAttribute("activePage", "main");
        return "datamarkingeditor/main.html";
    }
}
