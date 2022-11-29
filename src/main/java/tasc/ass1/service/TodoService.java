package tasc.ass1.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import tasc.ass1.entity.Todo;
import tasc.ass1.entity.dto.TodoDto;
import tasc.ass1.entity.myenum.Status;
import tasc.ass1.repository.TodoRepository;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class TodoService {
    @Autowired
    TodoRepository todoRepository;

    public Todo createItem(TodoDto todoDto) {
        Todo todo = Todo.builder()
                .detail(todoDto.getDetail())
                .createdAt(LocalDateTime.now())
                .status(Status.ACTIVE)
                .build();
        return todoRepository.save(todo);
    }

    public Page<Todo> findAll(Integer page, Integer limit, String sortBy) {
        Pageable pageable = PageRequest.of(page, limit, Sort.by(sortBy));
        Page<Todo> pageResult = todoRepository.findAll(pageable);
        if (pageResult.hasContent()) {
            return pageResult;
        } else {
            return (Page<Todo>) new ArrayList<Todo>();
        }
    }

    public Optional<Todo> findById(String id) {
        return todoRepository.findById(id);
    }

    public Todo updateItem(Todo obj) {
        return todoRepository.save(obj);
    }

    public void deleteItemById(String id) {
        Optional<Todo> optionalTodo = todoRepository.findById(id);
        if (optionalTodo.isPresent()) {
            Todo existedTodo = optionalTodo.get();
            existedTodo.setStatus(Status.INACTIVE);
            todoRepository.save(existedTodo);
        }
    }
}