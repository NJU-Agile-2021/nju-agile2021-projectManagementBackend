package com.nju.projectManagement.Service.BL;

import com.nju.projectManagement.VO.*;
import org.springframework.stereotype.Service;


import java.util.List;

/**
 * @author Toby Fu
 * @date 2022/2/7
 **/
@Service
public interface TaskService {
    ResponseVO<List<TaskListVO>> getTaskLists(int projectId);


    ResponseVO<Boolean> updateTask(TaskVO taskVO);

    ResponseVO<Boolean> deleteTask(int taskId);


    ResponseVO<Boolean> deleteTaskList( int taskListId);


    ResponseVO<Boolean> updateTaskListName(int taskListId, String name);

    ResponseVO<Boolean> createTaskList(int projectId, String name);


    ResponseVO<Boolean> createTask(CreateTaskForm createTaskForm);

    ResponseVO<TaskInfoVO> getTaskInfo(int taskId);

   ResponseVO<Boolean> assignTask(AssignTaskForm assignTaskForm);

   ResponseVO<List<UserTaskVO>> getUserTaskList(int userId);

   ResponseVO<Boolean> changeTaskFinishState(int taskId);

   ResponseVO<Boolean> checkTask(int taskId);

   ResponseVO<Boolean> addTaskLabel(int taskId, String label);

    ResponseVO<List<UserTaskVO>> getUncheckedTasks(int projectId);

}
