package it.itresources.springtut.springtutorial.controller;


import it.itresources.springtut.springtutorial.mapper.GradeMapper;
import it.itresources.springtut.springtutorial.model.request.NewGradeRequest;
import it.itresources.springtut.springtutorial.services.impl.ServiceGradeImpl;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/classrooms/{id}/grades")
public class GradeController {

    private final ServiceGradeImpl serviceGradeImpl;

    @Autowired
    public GradeController (ServiceGradeImpl serviceGradeImpl)
    {
        this.serviceGradeImpl=serviceGradeImpl;
    }

    @PostMapping("")
    @PreAuthorize("hasRole('ROLE_TEACHER')")
    public ResponseEntity<?> assignGrade (@PathVariable(value = "id") Long id, @Valid @RequestBody NewGradeRequest request)
    {
        Long classroomId=id;
        if(serviceGradeImpl.assignGrades(request)!=null)
        {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.badRequest().build();
        }
    }
}
