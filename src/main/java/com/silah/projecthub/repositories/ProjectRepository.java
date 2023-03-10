package com.silah.projecthub.repositories;

import com.silah.projecthub.entities.Project;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    List<Project> findByCategory(String category);

    List<Project> findAllByOrderByImpressionsDesc(Pageable pageable);
}
