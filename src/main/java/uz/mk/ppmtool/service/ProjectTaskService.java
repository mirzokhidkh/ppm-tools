package uz.mk.ppmtool.service;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.mk.ppmtool.domain.Backlog;
import uz.mk.ppmtool.domain.Project;
import uz.mk.ppmtool.domain.ProjectTask;
import uz.mk.ppmtool.exception.ProjectNotFoundException;
import uz.mk.ppmtool.repository.BacklogRepository;
import uz.mk.ppmtool.repository.ProjectRepository;
import uz.mk.ppmtool.repository.ProjectTaskRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ProjectTaskService {
    private final ProjectRepository projectRepository;
    private final ProjectTaskRepository projectTaskRepository;
    private final BacklogRepository backlogRepository;
    private final ProjectService projectService;

    public ProjectTask addProjectTask(String projectIdentifier, ProjectTask projectTask, String username) {

        Backlog backlog = projectService.findProjectByIdentifier(projectIdentifier, username).getBacklog();
        projectTask.setBacklog(backlog);
        Integer BacklogSequence = backlog.getPTSequence();
        BacklogSequence++;
        backlog.setPTSequence(BacklogSequence);
        projectTask.setProjectSequence(projectIdentifier + "-" + BacklogSequence);
        projectTask.setProjectIdentifier(projectIdentifier);

        if (Objects.equals(projectTask.getStatus(), "") || projectTask.getStatus() == null) {
            projectTask.setStatus("TO_DO");
        }

        if (projectTask.getPriority() == null || projectTask.getPriority() == 0) {
            projectTask.setPriority(3);
        }

        return projectTaskRepository.save(projectTask);
    }


    public Iterable<ProjectTask> getBacklogById(String id, String username) {
        projectService.findProjectByIdentifier(id, username);

        return projectTaskRepository.findByProjectIdentifier(id);
    }


    public ProjectTask findPTByProjectSequence(String backlogId, String ptId,String username) {
        projectService.findProjectByIdentifier(backlogId,username);

        ProjectTask projectTask = projectTaskRepository.findByProjectSequence(ptId);
        if (projectTask == null) {
            throw new ProjectNotFoundException("Project Task '" + ptId + "' not found");
        }

        if (!projectTask.getProjectIdentifier().equals(backlogId)) {
            throw new ProjectNotFoundException("Project Task '" + ptId + "' does not exist in project: '" + backlogId);
        }

        return projectTask;
    }

    public ProjectTask updateBYProjectSequence(ProjectTask updateProjectTask, String backlogId, String ptId,String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlogId, ptId,username);
        projectTask = updateProjectTask;
        return projectTaskRepository.save(projectTask);
    }


    public void deletePTByProjectSequence(String backlogId, String ptId,String username) {
        ProjectTask projectTask = findPTByProjectSequence(backlogId, ptId,username);
        projectTaskRepository.delete(projectTask);
    }


}
