package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.model.User;
import ru.job4j.todo.service.TaskService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/tasks")
public class TaskControllor {
    private final TaskService taskService;

    public TaskControllor(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getAllTasks(Model model) {
        model.addAttribute("tasks", taskService.findAll());
        return "tasks/list";
    }

    @GetMapping("/listComplete")
    public String getCompleteTasks(Model model) {
        model.addAttribute("tasks", taskService.findDoneTrue());
        return "tasks/list";
    }

    @GetMapping("/listNew")
    public String getNewTasks(Model model) {
        model.addAttribute("tasks", taskService.findDoneFalse());
        return "tasks/list";
    }

    @GetMapping("/create")
    public String createView() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, HttpSession session) {
        System.out.println(session.getAttribute("user"));
        task.setUser((User) session.getAttribute("user"));
        taskService.create(task);
        return "redirect:/tasks";
    }

    @GetMapping("/{id}")
    public String infoView(Model model, @PathVariable int id) {
        var getInfo = taskService.findById(id);
        if (getInfo.isEmpty()) {
            model.addAttribute("message", "Задача не найдена");
            return "errors/404";
        }
        model.addAttribute("task", getInfo.get());
        return "tasks/info";
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        if (!taskService.delete(id)) {
            model.addAttribute("message", "Не удалось удалить");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @GetMapping("/edit/{id}")
    public String editView(Model model, @PathVariable int id) {
        Optional<Task> optionalTask = taskService.findById(id);
        if (optionalTask.isEmpty()) {
            model.addAttribute("message", "Не удалось найти задачу для редактирования");
            return "errors/404";
        }
        model.addAttribute("task", taskService.findById(id));
        return "tasks/edit";
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Task task, Model model) {
        if (!taskService.update(task.getId(), task)) {
            model.addAttribute("message", "Не удалось обновить");
            return "errors/404";
        }
        return "redirect:/tasks";
    }

    @PostMapping("/complete")
    public String complete(@ModelAttribute Task task, Model model) {
        if (!taskService.complete(task.getId())) {
            model.addAttribute("message", "Не удалось выполнить");
            return "errors/404";
        }
        return "redirect:/tasks";
    }
}
