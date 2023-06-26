package net.bean.java.task.executor;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

@RestController
public class TaskResource {

    private final List<Task> tasks = Collections.synchronizedList(new ArrayList<>());

    private final ExecutorService executor = Executors.newFixedThreadPool(10);

    @Autowired
    private RabbitTemplate template;

    @GetMapping("/tasks")
    public ResponseEntity<TaskCollection> getAllTasks() {
        return ResponseEntity.ok(new TaskCollection(tasks));
    }

    @PostMapping("/tasks")
    public ResponseEntity<Task> createNewTask() {
        String taskId = String.valueOf(System.currentTimeMillis());
        Task task = new Task(taskId, new AtomicLong(0), template);
        tasks.add(0, task);
        executor.submit(task);
        return ResponseEntity.created(null).body(task);
    }

    @DeleteMapping("/tasks/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable("id") String id) {
        Optional<Task> task = tasks.stream()
                                   .filter(t -> t.getId().equals(id))
                                   .filter(t -> t.getProgress() == 100l)
                                   .findFirst();
        task.ifPresent(tasks::remove);
        return ResponseEntity.noContent().build();
    }
}
