package jp.ymshita.spring_todo.domain;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Table(name = "task")
@Data
@AllArgsConstructor
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	Integer id;

	String subject;

	@JsonProperty("dead-line")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	LocalDate deadLine;

	@JsonProperty("done")
	Boolean hasDone;

	public Task() {

	}

	public Task(String subject, LocalDate deadLine, Boolean hasDone) {
		this.subject = subject;
		this.deadLine = deadLine;
		this.hasDone = hasDone;
	}
}
