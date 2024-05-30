package ru.job4j.todo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.job4j.todo.model.Task;
import ru.job4j.todo.service.TaskService;

@Controller
@RequestMapping("/tasks")
public class TaskControllor {
    private final TaskService taskService;

    public TaskControllor(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public String getAllTasks(Model model) {
        try {
            model.addAttribute("tasks", taskService.findAll());
            return "tasks/list";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/listComplete")
    public String getCompleteTasks(Model model) {
        try {
            model.addAttribute("tasks", taskService.findDoneTrue());
            return "tasks/list";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/listNew")
    public String getNewTasks(Model model) {
        try {
            model.addAttribute("tasks", taskService.findDoneFalse());
            return "tasks/list";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/create")
    public String createView() {
        return "tasks/create";
    }

    @PostMapping("/create")
    public String create(@ModelAttribute Task task, Model model) {
        try {
            taskService.create(task);
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/{id}")
    public String infoView(Model model, @PathVariable int id) {
        try {
            model.addAttribute("task", taskService.findById(id));
            return "tasks/info";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable int id, Model model) {
        try {
            taskService.delete(id);
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }

    }

    @GetMapping("/edit/{id}")
    public String editView(Model model, @PathVariable int id) {
        try {
            model.addAttribute("task", taskService.findById(id));
            return "tasks/edit";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @PostMapping("/edit")
    public String edit(@ModelAttribute Task task, Model model) {
        try {
            taskService.update(task.getId(), task);
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }

    @PostMapping("/complete")
    public String complete(@ModelAttribute Task task, Model model) {
        try {
            task.setDone(true);
            taskService.update(task.getId(), task);
            return "redirect:/tasks";
        } catch (Exception e) {
            model.addAttribute("message", e.getMessage());
            return "errors/404";
        }
    }
}
