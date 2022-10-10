package it.itresources.springtut.springtutorial.controller;


import it.itresources.springtut.springtutorial.services.impl.ServiceGradeImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/classrooms/{id}/grades/")
public class GradeController {

    private final ServiceGradeImpl serviceGradeImpl;

    @Autowired
    public GradeController (ServiceGradeImpl serviceGradeImpl)
    {
        this.serviceGradeImpl=serviceGradeImpl;
    }


}
