
package com.example.bugtrackingsystem;

import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/bugs")
@CrossOrigin
public class BugController {

    private final BugRepository bugRepository;

    public BugController(BugRepository bugRepository) {
        this.bugRepository = bugRepository;
    }

    @GetMapping
    public List<Bug> getAllBugs() {
        return bugRepository.findAll();
    }

    @GetMapping("/{id}")
    public Bug getBugById(@PathVariable Long id) {
        return bugRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bug not found: " + id));
    }

    @PostMapping
    public Bug createBug(@RequestBody Bug bug) {
        if (bug.getStatus() == null || bug.getStatus().isBlank()) {
            bug.setStatus("OPEN");
        }
        if (bug.getPriority() == null || bug.getPriority().isBlank()) {
            bug.setPriority("MEDIUM");
        }
        return bugRepository.save(bug);
    }

    @PutMapping("/{id}")
    public Bug updateBug(@PathVariable Long id, @RequestBody Bug updatedBug) {
        Bug bug = bugRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Bug not found: " + id));

        bug.setTitle(updatedBug.getTitle());
        bug.setDescription(updatedBug.getDescription());
        bug.setPriority(updatedBug.getPriority());
        bug.setStatus(updatedBug.getStatus());

        return bugRepository.save(bug);
    }

    @DeleteMapping("/{id}")
    public void deleteBug(@PathVariable Long id) {
        bugRepository.deleteById(id);
    }
}
