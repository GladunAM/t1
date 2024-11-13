package ru.t1.gladun.tests.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.t1.gladun.tests.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
}
