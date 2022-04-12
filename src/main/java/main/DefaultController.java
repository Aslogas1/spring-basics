package main;

import main.model.ToDo;
import main.model.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DefaultController {
    @Autowired
    private final ToDoRepository toDoRepository;

    public DefaultController(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }


    @RequestMapping("/")
    public String index(Model model) {
        Iterable<ToDo> toDoIterable = toDoRepository.findAll();
        List<ToDo> toDos = new ArrayList<>();

        for (ToDo toDo : toDoIterable) {
            toDos.add(toDo);
        }
        model.addAttribute("todos", toDos)
                .addAttribute("todosCount", toDos.size());
        return "index";
    }
}
