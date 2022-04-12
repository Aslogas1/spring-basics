package main;

import main.model.ToDo;
import main.model.ToDoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ToDoController {
    @Autowired
    private final ToDoRepository toDoRepository;

    public ToDoController(ToDoRepository toDoRepository) {
        this.toDoRepository = toDoRepository;
    }

    @GetMapping("/todos/")
    public List<ToDo> list() {
        Iterable<ToDo> toDoIterable = toDoRepository.findAll();

        List<ToDo> toDos = new ArrayList<>();
        for (ToDo toDo : toDoIterable) {
            toDos.add(toDo);
        }
        return toDos;
    }

    @PostMapping("/todos/")
    public int add(ToDo toDo) {
        ToDo newToDo = toDoRepository.save(toDo);
        return newToDo.getId();
    }

    @GetMapping("/todos/{id}")
    public ResponseEntity<?> get(@PathVariable int id) {
        Optional<ToDo> optionalTodo = toDoRepository.findById(id);

        if (!optionalTodo.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        return new ResponseEntity<>(optionalTodo.get(), HttpStatus.OK);
    }
}
