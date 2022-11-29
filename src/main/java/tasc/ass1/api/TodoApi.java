package tasc.ass1.api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tasc.ass1.entity.Todo;
import tasc.ass1.entity.dto.TodoDto;
import tasc.ass1.service.TodoService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/v1")
@CrossOrigin("*")
public class TodoApi {
    @Autowired
    TodoService todoService;

    @RequestMapping(method = RequestMethod.GET)
    public Page<Todo> findAll(@RequestParam(defaultValue = "0") int page,
                              @RequestParam(defaultValue = "5") int limit,
                              @RequestParam(defaultValue = "id") String sortBy) {
        return todoService.findAll(page, limit, sortBy);
    }

    @RequestMapping(method = RequestMethod.GET, path = "{id}")
    public ResponseEntity<?> findItemById(@PathVariable String id) {
        Optional<Todo> optionalTodo = todoService.findById(id);
        if (optionalTodo.isPresent()) {
            return ResponseEntity.ok(optionalTodo);
        }
        return new ResponseEntity<>("Todo not found", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createItem(@RequestBody TodoDto obj) {
        return ResponseEntity.ok(todoService.createItem(obj));
    }

    @RequestMapping(method = RequestMethod.PUT, path = "{id}")
    public ResponseEntity<?> updateItem(@PathVariable String id,
                                        @RequestBody Todo obj) {
        Optional<Todo> optionalTodo = todoService.findById(id);
        if (optionalTodo.isPresent()) {
            Todo updatedTodo = optionalTodo.get();
            updatedTodo.setDetail(obj.getDetail());
            updatedTodo.setUpdatedAt(LocalDateTime.now());
            updatedTodo.setCreatedAt(updatedTodo.getCreatedAt());
            updatedTodo.setStatus(obj.getStatus());
            return ResponseEntity.ok(todoService.updateItem(updatedTodo));
        }
        return ResponseEntity.badRequest().build();
    }

    @RequestMapping(method = RequestMethod.PUT, path = "status/{id}")
    public ResponseEntity<?> changeStatus(@PathVariable String id,
                                          @RequestBody Todo obj) {
        Optional<Todo> optionalTodo = todoService.findById(id);
        if (optionalTodo.isPresent()) {
            Todo updatedItem = optionalTodo.get();
            updatedItem.setUpdatedAt(LocalDateTime.now());
            updatedItem.setStatus(obj.getStatus());
            return ResponseEntity.ok(todoService.updateItem(updatedItem));
        }
        return new ResponseEntity<>("Update fail", HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "{id}")
    public ResponseEntity<?> deleteItemById(@PathVariable String id) {
        Optional<Todo> optionalTodo = todoService.findById(id);
        if (!optionalTodo.isPresent()) {
            return new ResponseEntity<>("Todo not found", HttpStatus.NOT_FOUND);
        }
        todoService.deleteItemById(id);
        return new ResponseEntity<>("Delete success", HttpStatus.OK);
    }
}
