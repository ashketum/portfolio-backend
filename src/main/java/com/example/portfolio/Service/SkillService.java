package com.example.portfolio.Service;

import com.example.portfolio.Exception.ResourceNotFoundException;
import com.example.portfolio.Model.Skill;
import com.example.portfolio.Repository.SkillRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SkillService {

    @Autowired
    private SkillRepo repo;

    public List<Skill> getAllSkill() {
        return repo.findAll();
    }

    public ResponseEntity<String> addSkill(Skill skill) {
        if( skill == null || skill.getSkillName() == null || skill.getSkillName().isBlank() ||
                skill.getSkillIconUrl() == null || skill.getSkillIconUrl().isBlank()) {
            return new ResponseEntity<>("Missing Info", HttpStatus.BAD_REQUEST);
        }
        repo.save(skill);
        return new ResponseEntity<>("Successfully Added !!!", HttpStatus.OK);
    }

    public ResponseEntity<String> updateSkill(Long skillId, Skill skill) {

        Skill oldSkill = repo.findById(skillId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Skill with skill ID: " + skillId + " not found."));


        if( skill == null || skill.getSkillName().isEmpty() || skill.getSkillName().isBlank() ||
                skill.getSkillIconUrl().isEmpty() || skill.getSkillIconUrl().isBlank()) {
            return new ResponseEntity<>("Missing Info", HttpStatus.BAD_REQUEST);
        }

        oldSkill.setSkillName(skill.getSkillName());
        oldSkill.setSkillIconUrl(skill.getSkillIconUrl());
        repo.save(oldSkill);
        return new ResponseEntity<>("Successfully Updated !!!", HttpStatus.OK);
    }

    public ResponseEntity<String> deleteSkillById(Long id) {
        if(!repo.existsById(id)) {
            throw new ResourceNotFoundException(
                    "Skill with ID: " + id + " not found"
            );
        }
        repo.deleteById(id);
        return new ResponseEntity<>("Successfully Deleted this skill!!", HttpStatus.OK);
   }
}
