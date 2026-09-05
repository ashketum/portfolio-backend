package com.example.portfolio.Controller;

import com.example.portfolio.Model.Skill;
import com.example.portfolio.Service.SkillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "${frontend.url}")
@RestController
@RequestMapping("/skills")
public class SkillController {

    @Autowired
    private SkillService service;

    @GetMapping
    public List<Skill> getAllSkill(){
        return service.getAllSkill();
    }

    @PostMapping
    public ResponseEntity<String> addSkill(@RequestBody Skill skill) {
        return service.addSkill(skill);
    }

    @PutMapping(path = "/{skillId}")
    public ResponseEntity<String> updateSkill(@PathVariable Long skillId, @RequestBody Skill skill) {
        return service.updateSkill(skillId, skill);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProjectSkillById(@PathVariable Long id) {
        return service.deleteSkillById(id);
    }

}
