package uz.mk.ppmtool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.mk.ppmtool.domain.Project;
import uz.mk.ppmtool.domain.User;
import uz.mk.ppmtool.security.CurrentUser;
import uz.mk.ppmtool.service.MapValidationErrorService;
import uz.mk.ppmtool.service.ProjectService;

import javax.validation.Valid;
import java.security.Principal;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/project")
@CrossOrigin
public class ProjectController {
    private final ProjectService projectService;
    private final MapValidationErrorService mapValidationErrorService;

    @PostMapping
    public ResponseEntity<?> createOrEdit(@Valid @RequestBody Project project,  @CurrentUser User user) {

//        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
//        if (errorMap != null) {
//            return errorMap;
//        }
        Project saveOrEditProject = projectService.saveOrEditProject(project, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(saveOrEditProject);
    }

    @GetMapping("/{projectId}")
    public HttpEntity<?> getProjectById(@PathVariable String projectId,@CurrentUser User user) {
        Project project = projectService.findProjectByIdentifier(projectId,user.getUsername());
        return ResponseEntity.ok(project);
    }

    @GetMapping("/all")
    public Iterable<Project> getALlProjects(@CurrentUser User user) {
        return projectService.findAllProjects(user.getUsername());
    }

    @DeleteMapping("/{projectId}")
    public HttpEntity<?> deleteProject(@PathVariable String projectId,@CurrentUser User user) {
        projectService.deleteProjectByIdentifierId(projectId,user.getUsername());
        return ResponseEntity.ok("Project with ID '" + projectId + " was deleted");
    }



}
