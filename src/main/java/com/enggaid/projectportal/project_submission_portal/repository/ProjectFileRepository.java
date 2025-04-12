package com.enggaid.projectportal.project_submission_portal.repository;

import com.enggaid.projectportal.project_submission_portal.model.ProjectFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectFileRepository extends JpaRepository<ProjectFile, Long> {

    // Get a single project file by project head's ID
    Optional<ProjectFile> findByProjectGroup_ProjectHead_Id(Long id);

    // Get all project files by project head's ID
    List<ProjectFile> findAllByProjectGroup_ProjectHead_Id(Long id);

    // Get all project files by project head's username
    List<ProjectFile> findAllByProjectGroup_ProjectHead_Email(String email);
}
