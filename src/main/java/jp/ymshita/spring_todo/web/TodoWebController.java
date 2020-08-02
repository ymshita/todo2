package jp.ymshita.spring_todo.web;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import jp.ymshita.spring_todo.domain.Task;
import jp.ymshita.spring_todo.form.TaskForm;
import jp.ymshita.spring_todo.service.TodoService;

@Controller
public class TodoWebController {
	private static final String TASKS = "tasks";
	private static final String REDIRECT_TO = "redirect:/" + TASKS;

	@Autowired // to injection
	TodoService todoService;

	@ResponseBody
	@GetMapping(value = "/tasks")
	public String readAllTasks() {
//		TaskForm initialForm = createInitialForm();
//
//		ModelAndView modelAndView = toTasksPage();
//		modelAndView.addObject("form", initialForm);
//
//		List<Task> tasks = todoService.findAllTasks();
//		modelAndView.addObject(TASKS, tasks);

//		return modelAndView;
		return "{\"hoge\":\"fuga\"}";
	}

	@PostMapping(value = "/tasks")
	public ModelAndView createOneTask(@ModelAttribute TaskForm form) {
		createTaskFromForm(form);
		return new ModelAndView(REDIRECT_TO);
	}

	@GetMapping(value = "/tasks/{id}")
	public ModelAndView readOneTask(@PathVariable Integer id) {
		Optional<TaskForm> formFromId = readTaskFromId(id);
		if (!formFromId.isPresent()) {
			return new ModelAndView(REDIRECT_TO); // redirect to "readAllTasks()"
		}

		ModelAndView modelAndView = toTasksPage();
		modelAndView.addObject("taskId", id);
		modelAndView.addObject("form", formFromId.get());

		List<Task> tasks = todoService.findAllTasks();
		modelAndView.addObject(TASKS, tasks);

		return modelAndView;
	}

	@PutMapping(value = "/tasks/{id}")
	public ModelAndView updateOneTask(
			@PathVariable Integer id, 
			@ModelAttribute TaskForm form) {
		
		updateTask(id, form);
		return new ModelAndView(REDIRECT_TO);
	}

	@DeleteMapping(value = "/tasks/{id}")
	public ModelAndView deleteOneTask(@PathVariable Integer id) {
		deleteTask(id);
		return new ModelAndView(REDIRECT_TO);
	}
	
	
	private ModelAndView toTasksPage() {
		return new ModelAndView(TASKS);
	}

	private TaskForm createInitialForm() {
		String formSubject = "";
		LocalDate formDeadLine = LocalDate.now();
		Boolean isNewTask = true;
		Boolean hasDone = false;
		return new TaskForm(formSubject, formDeadLine, hasDone, isNewTask);
	}

	private void createTaskFromForm(TaskForm form) {
		String subject = form.getSubject();
		LocalDate deadLine = form.getDeadLine();
		Boolean hasDone = form.getHasDone();
		Task task = new Task(subject, deadLine, hasDone);
		todoService.createTask(task);
	}

	private Optional<TaskForm> readTaskFromId(Integer id) {
		Optional<Task> task = todoService.findOneTask(id);
		if (!task.isPresent()) {
			return Optional.ofNullable(null);
		}
		String formSubject = task.get().getSubject();
		LocalDate formDeadLine = task.get().getDeadLine();
		Boolean hasDone = task.get().getHasDone();
		Boolean isNewTask = false;
		TaskForm form = new TaskForm(formSubject, formDeadLine, hasDone, isNewTask);
		return Optional.ofNullable(form);
	}

	private void updateTask(Integer id, TaskForm form) {
		String subject = form.getSubject();
		LocalDate deadLine = form.getDeadLine();
		Boolean hasDone = form.getHasDone();
		Task task = new Task(id, subject, deadLine, hasDone);
		todoService.updateTask(task);
	}
	
	private void deleteTask(Integer id) {
		Optional<Task> task = todoService.findOneTask(id);
		if(task.isPresent()) {
			todoService.deleteTask(id);
		}
	}
}
