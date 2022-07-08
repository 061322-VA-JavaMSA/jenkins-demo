package com.revature.models;

import java.time.LocalDate;
import java.util.Objects;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;





@Entity
@Table(name="tasks")
public class Task {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String description;
	@Temporal(TemporalType.DATE)
	@Column(name="due_date")
	private LocalDate dueDate;
	private String status;
	@ManyToOne
    @JoinColumn(name = "user_assigned_id")
	private User userAssigned;
	
	public Task() {
		super();
		// TODO Auto-generated constructor stub
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public LocalDate getDueDate() {
		return dueDate;
	}
	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public User getUserAssigned() {
		return userAssigned;
	}
	public void setUserAssigned(User userAssigned) {
		this.userAssigned = userAssigned;
	}
	@Override
	public String toString() {
		return "Task [id=" + id + ", description=" + description + ", dueDate=" + dueDate + ", status=" + status
				+ ", userAssigned=" + userAssigned + "]";
	}
	@Override
	public int hashCode() {
		return Objects.hash(description, dueDate, id, status, userAssigned);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		return Objects.equals(description, other.description) && Objects.equals(dueDate, other.dueDate)
				&& id == other.id && Objects.equals(status, other.status)
				&& Objects.equals(userAssigned, other.userAssigned);
	}
}
