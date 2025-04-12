package com.enggaid.projectportal.project_submission_portal.controller;

import com.enggaid.projectportal.project_submission_portal.model.ProjectGroup;
import com.enggaid.projectportal.project_submission_portal.model.ProjectMember;
import com.enggaid.projectportal.project_submission_portal.model.User;
import com.enggaid.projectportal.project_submission_portal.service.ProjectGroupService;
import com.enggaid.projectportal.project_submission_portal.service.ProjectHeadService;
import com.enggaid.projectportal.project_submission_portal.service.ProjectMemberService;
import com.enggaid.projectportal.project_submission_portal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/projecthead")
public class ProjectHeadController {

    @Autowired
    private UserService userService;

    @Autowired
    private ProjectGroupService projectGroupService;

    @Autowired
    private ProjectHeadService projectHeadService;

    @Autowired
    private ProjectMemberService projectMemberService;



    @GetMapping("/dashboard")
    public String showDashboard(Model model, Principal principal){
        String username = principal.getName();
        List<ProjectMember>projectMembers=projectMemberService.getAllMembers();
        Optional<User> projectHead = Optional.ofNullable(userService.findByUsername(username));
        model.addAttribute("projectMemberList", projectMemberService.getAllMembers());

        if(projectHead.isPresent()){
            User loggedInProjectHead =projectHead.get();
            model.addAttribute("user",loggedInProjectHead);

            ProjectGroup projectGroup =projectGroupService.getProjectGroupByProjectHeadId(loggedInProjectHead.getId());
            model.addAttribute("projectGroup", "ProjectHead Not Found");


            if (projectGroup != null){
                model.addAttribute("projectMember", projectGroup.getMembers());
            }else {
                model.addAttribute("message","ProjectHead Not Found");
                return "Error";
            }

        }

        return "projecthead/projecthead-dashboard";

    }

    @GetMapping("/projectgroup")
    public String getProjectGroup(Model model, Principal principal){
        String username = principal.getName();
        Optional<User> projecthead =Optional.ofNullable(userService.findByUsername(username));

        if (projecthead.isPresent()){
            User loggedInProjectHead =projecthead.get();
            ProjectGroup projectGroup=projectGroupService.getProjectGroupByProjectHeadId(loggedInProjectHead.getId());
            if (projectGroup!=null){
                model.addAttribute("projectgroup", List.of(projectGroup));
            }else {
                model.addAttribute("projectgroup", Collections.emptyList());
                model.addAttribute("message", "ProjectHead Not Found");
            }
        }else {
            model.addAttribute("message", "ProjectHead Not Found");
        }
        return "projecthead/projectgroup";
    }

    @GetMapping("/projectgroup/new")
    public String getNewProjectGroupForm(Model model, Principal principal, RedirectAttributes redirectAttributes){
        String username = principal.getName();
        Optional<User> projecthead =Optional.ofNullable(userService.findByUsername(username));

        if (projecthead.isPresent()){
            ProjectGroup projectGroup = new ProjectGroup();
            projectGroup.setProjectHead(projecthead.get());
            model.addAttribute("projectgroup",projectGroup);
            return "projecthead/projectgroup-form";
        }else {
            redirectAttributes.addFlashAttribute("error","Project HEAD Not Found Please try again");
            return "redirect:/login";
        }
    }


    //view and edit ProjectGroup

    @GetMapping("/projectgroup/{id}/edit")
    public String editProjectGroupForm(@PathVariable("id") Long groupid, Model model, Principal principal) {
        User projecthead = userService.findByUsername(principal.getName());
        ProjectGroup projectGroup = projectGroupService.getProjectGroupById(groupid).orElse(null);

        if (projecthead != null && projectGroup.getProjectHead().getId().equals(projecthead.getId())) {
            model.addAttribute("projectgroup", projectGroup);
            return "projecthead/member-form";
        } else {
            model.addAttribute("error", "You are not authorized to edit this team");
            return "redirect:/projecthead/projectgroup";
        }
    }
}
