package main;


import main.model.Task;
import main.model.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.Optional;

@Controller
public class TodoController {

    @Autowired
    private TaskRepository taskRepository;

    @GetMapping("/todos")
    public String todoMain(Model model) {
        Iterable<Task> tasks = taskRepository.findAll();
        model.addAttribute("tasks", tasks);
        return "todos-main";
    }

    @GetMapping("/todos/add")
    public String todoAdd(Model model) {
        return "todos-add";
    }

    @PostMapping("/todos/add")
    public String todosTaskAdd(@RequestParam String name, @RequestParam String description, Model model) {
        Task task = new Task(name, description);
        taskRepository.save(task);
        return "redirect:/todos";
    }

    @GetMapping("/todos/{id}")
    public String todosDetails(@PathVariable(value = "id") int id, Model model) {
        if (!taskRepository.existsById(id)) {
            return "redirect:/todos";
        }
        Optional<Task> task = taskRepository.findById(id);
        ArrayList<Task> tasks = new ArrayList<>();
        task.ifPresent(tasks::add);
        model.addAttribute("task", tasks);
        return "todos-details";
    }

    @GetMapping("/todos/{id}/edit")
    public String todosEdit(@PathVariable(value = "id") int id, Model model) {
        if (!taskRepository.existsById(id)) {
            return "redirect:/todos";
        }
        Optional<Task> post = taskRepository.findById(id);
        ArrayList<Task> tasks = new ArrayList<>();
        post.ifPresent(tasks::add);
        model.addAttribute("post", tasks);
        return "todos-edit";
    }

    @PostMapping("/todos/{id}/edit")
    public String todosPostUpdate(@PathVariable(value = "id") int id, @RequestParam String name,
                                 @RequestParam String description, Model model) {
        Task task = taskRepository.findById(id).orElseThrow();
        task.setName(name);
        task.setDescription(description);
        taskRepository.save(task);
        return "redirect:/todos";
    }

    @PostMapping("/todos/{id}/remove")
    public String taskPostDelete(@PathVariable(value = "id") int id, Model model) {
        Task task = taskRepository.findById(id).orElseThrow();
        taskRepository.delete(task);
        return "redirect:/todos";
    }

}
