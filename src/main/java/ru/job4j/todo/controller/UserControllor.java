package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/users")
public class UserControllor {
    private UserService userService;

    public UserControllor(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String viewRegistration() {
        return "users/registration";
    }

    @PostMapping("/registration")
    public String registration(Model model, @ModelAttribute User user) {
        var isUser = userService.findByLogin(user.getLogin());
        if (isUser.isPresent()) {
            model.addAttribute("message", "Данный логин уже используется");
            return "errors/404";
        }
        userService.create(user);
        return "redirect:/users/login";
    }

    @GetMapping("/login")
    public String viewLogin() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(Model model, @ModelAttribute User user, HttpServletRequest request) {
        var isUser = userService.findByLoginAndPassword(user.getLogin(), user.getPassword());
        if (isUser.isEmpty()) {
            model.addAttribute("message", "Неверный логин или пароль");
            return "errors/404";
        }
        var session = request.getSession();
        session.setAttribute("user", isUser.get());
        return "redirect:/tasks";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }
}
