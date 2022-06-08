package uz.mk.ppmtool.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import uz.mk.ppmtool.domain.ProjectTask;
import uz.mk.ppmtool.domain.User;
import uz.mk.ppmtool.repository.ProjectTaskRepository;
import uz.mk.ppmtool.security.CurrentUser;
import uz.mk.ppmtool.service.MapValidationErrorService;
import uz.mk.ppmtool.service.ProjectTaskService;

import javax.validation.Valid;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/backlog")
@CrossOrigin
public class BacklogController {

    private final ProjectTaskService projectTaskService;
    private final MapValidationErrorService mapValidationErrorService;

    @PostMapping("/{backlog_id}")
    public ResponseEntity<?> addPTtoBacklog(@Valid @RequestBody ProjectTask projectTask,
                                            BindingResult result, @PathVariable String backlog_id, @CurrentUser User user) {
//        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
//        if (errorMap != null) return errorMap;

        ProjectTask savedProjectTask = projectTaskService.addProjectTask(backlog_id, projectTask, user.getUsername());
        return ResponseEntity.status(HttpStatus.CREATED).body(savedProjectTask);
    }

    @GetMapping("/{backlog_id}")
    public Iterable<ProjectTask> getProjectBacklog(@PathVariable String backlog_id, @CurrentUser User user) {
        return projectTaskService.getBacklogById(backlog_id, user.getUsername());
    }


    @GetMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> getProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,@CurrentUser User user) {
        ProjectTask projectTask = projectTaskService.findPTByProjectSequence(backlog_id, pt_id,user.getUsername());
        return ResponseEntity.status(HttpStatus.OK).body(projectTask);
    }


    @PatchMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> update(@Valid @RequestBody ProjectTask projectTask, BindingResult result,
                                    @PathVariable String backlog_id, @PathVariable String pt_id,@CurrentUser User user) {
//        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
//        if (errorMap != null) return errorMap;

        ProjectTask updatedProjectTask = projectTaskService.updateBYProjectSequence(projectTask, backlog_id, pt_id,user.getUsername());

        return ResponseEntity.status(HttpStatus.OK).body(updatedProjectTask);
    }


    @DeleteMapping("/{backlog_id}/{pt_id}")
    public ResponseEntity<?> deleteProjectTask(@PathVariable String backlog_id, @PathVariable String pt_id,@CurrentUser User user) {
        projectTaskService.deletePTByProjectSequence(backlog_id, pt_id,user.getUsername());

        return ResponseEntity.ok("Project Task " + pt_id + " was deleted successfully");
    }
}
