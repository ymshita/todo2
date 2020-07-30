package jp.ymshita.spring_todo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import jp.ymshita.spring_todo.domain.Task;

@Repository
public interface TodoRepository extends JpaRepository<Task, Integer>{
}
