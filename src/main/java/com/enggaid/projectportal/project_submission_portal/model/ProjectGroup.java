package com.enggaid.projectportal.project_submission_portal.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class ProjectGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupid;

    private String projectTitle;
    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectStatus status;

    public ProjectStatus getStatus() {
        return status;
    }

    public void setStatus(ProjectStatus status) {
        this.status = status;
    }



    @OneToOne
    @JoinColumn(name = "head_id")
    private ProjectHead projectHead;


    @OneToMany(mappedBy = "projectGroup", cascade =CascadeType.ALL)
    private List<ProjectMember> members;

    @OneToMany(mappedBy = "projectGroup", cascade=CascadeType.ALL)
    private List<ProjectFile>projectFiles;

    private String submissionDate;

    public Long getGroupid() {
        return groupid;
    }

    public void setGroupid(Long groupid) {
        this.groupid = groupid;
    }

    public String getSubmissionDate() {
        return submissionDate;
    }

    public void setSubmissionDate(String submissionDate) {
        this.submissionDate = submissionDate;
    }

    public List<ProjectFile> getProjectFiles() {
        return projectFiles;
    }

    public void setProjectFiles(List<ProjectFile> projectFiles) {
        this.projectFiles = projectFiles;
    }

    public List<ProjectMember> getMembers() {
        return members;
    }

    public void setMembers(List<ProjectMember> members) {
        this.members = members;
    }

    public ProjectHead getProjectHead() {
        return projectHead;
    }

    public void setProjectHead(ProjectHead projectHead) {
        this.projectHead = projectHead;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public void setProjectTitle(String projectTitle) {
        this.projectTitle = projectTitle;
    }
}
