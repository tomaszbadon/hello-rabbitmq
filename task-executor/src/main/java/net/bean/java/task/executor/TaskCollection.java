package net.bean.java.task.executor;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TaskCollection {

    private List<Task> tasks;

}
