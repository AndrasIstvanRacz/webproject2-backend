package com.webproject.application.repository;

import com.webproject.application.entity.ToDoItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ToDoItemRepository extends JpaRepository<ToDoItem,Integer> {

}
