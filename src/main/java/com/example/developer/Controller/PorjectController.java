package com.example.developer.Controller;
import com.example.developer.DTO.ProjectDto;
import com.example.developer.Service.ProjectServices;
//import com.example.developer.DTO.ProjectDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/developer/projects")
public class PorjectController {
    private final ProjectServices projectServices;
    @PostMapping("/addproject")
    public ResponseEntity<?> addproject(@Valid @RequestBody ProjectDto projectdto)
    {
        System.out.println("data at the controller"+projectdto);
        return projectServices.addproject(projectdto);
    }
    @PutMapping("{id}")
    public ResponseEntity<?> updateproject(@PathVariable Long id ,@Valid @RequestBody ProjectDto projectDto)
    {
        return projectServices.updateprojects(id, projectDto);
    }
    @DeleteMapping("{id}")
    public ResponseEntity<?> deletemyproject(@PathVariable Long id )
    {
        return projectServices.deleteproject(id);

    }
    @GetMapping("/all/myproject")
    public ResponseEntity<?> showallproject()
    {
        return projectServices.getallproject();
    }
}
