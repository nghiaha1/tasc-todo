package tasc.ass1.seeder;

import com.github.javafaker.Faker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tasc.ass1.entity.Todo;
import tasc.ass1.entity.myenum.Status;
import tasc.ass1.repository.TodoRepository;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TodoSeeder {
    @Autowired
    TodoRepository todoRepository;

    public static List<Todo> todos = new ArrayList<>();
    public void generate() {
        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            Todo todo = new Todo();
            todo.setDetail(faker.lorem().paragraph(3));
            int randomNumber = faker.number().numberBetween(0, 3);
            if (randomNumber == 0) {
                todo.setStatus(Status.INACTIVE);
            } else if (randomNumber == 1) {
                todo.setStatus(Status.ACTIVE);
            } else if (randomNumber == 2) {
                todo.setStatus(Status.DONE);
            }
            todos.add(todo);
        }
        todoRepository.saveAll(todos);
    }
}
