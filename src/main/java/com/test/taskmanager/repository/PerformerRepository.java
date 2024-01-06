package com.test.taskmanager.repository;

import com.test.taskmanager.entity.Performer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformerRepository extends JpaRepository<Performer, Long> {

    List<Performer> findByEmail(String performerEmail);

}
