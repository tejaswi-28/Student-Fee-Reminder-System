package com.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.example.entity.AssignedEmailTemplate;
import com.example.entity.EmailTemplate;
import com.example.repository.AssignedEmailTemplateRepository;
import com.example.repository.EmailTemplateRepository;
import com.example.services.EmailTemplateService;

import java.io.IOException;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/EmailTemplates")
public class EmailTemplateController {

    @Autowired
    private EmailTemplateService emailTemplateService;

    @Autowired
    private AssignedEmailTemplateRepository assignedEmailTemplateRepository;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @PostMapping("/create")
    public String createEmailTemplate(@RequestParam("subject") String subject,
                                      @RequestParam("body") String body,
                                      @RequestParam("attachment") MultipartFile attachment) {
        try {
            emailTemplateService.saveEmailTemplate(subject, body, attachment);
            return "Email template created successfully!";
        } catch (IOException e) {
            return "Error while creating email template: " + e.getMessage();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmailTemplate> getEmailTemplate(@PathVariable Long id) {
        EmailTemplate emailTemplate = emailTemplateService.getEmailTemplateById(id);
        return emailTemplate != null ? ResponseEntity.ok(emailTemplate) : ResponseEntity.notFound().build();
    }

    @GetMapping("/attachment/{id}")
    public ResponseEntity<byte[]> getAttachment(@PathVariable Long id) {
        EmailTemplate emailTemplate = emailTemplateService.getEmailTemplateById(id);
        if (emailTemplate != null && emailTemplate.getAttachmentData() != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + emailTemplate.getAttachmentName() + "\"")
                    .contentType(MediaType.parseMediaType(emailTemplate.getAttachmentType()))
                    .body(emailTemplate.getAttachmentData());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/email-templates")
    public List<EmailTemplate> getEmailTemplates() {
        return emailTemplateRepository.findAll();
    }

    @PostMapping("/assign")
    public ResponseEntity<?> assignTemplate(@RequestBody AssignedEmailTemplate assignment) {
        try {
            AssignedEmailTemplate savedAssignment = emailTemplateService.saveAssignment(assignment);
            return ResponseEntity.ok(savedAssignment);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/assigned")
    public List<AssignedEmailTemplate> getAssignedTemplates() {
        return assignedEmailTemplateRepository.findAll();
    }

    @PutMapping("/updateFlag/{id}")
    public ResponseEntity<String> updateFlag(@PathVariable Long id, @RequestBody AssignedEmailTemplate updateData) {
        AssignedEmailTemplate assignment = assignedEmailTemplateRepository.findById(id).orElse(null);
        if (assignment != null) {
            assignment.setFlag(updateData.getFlag());
            assignedEmailTemplateRepository.save(assignment);
            return ResponseEntity.ok("Flag updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/updateDay/{id}")
    public ResponseEntity<String> updateDay(@PathVariable Long id, @RequestBody AssignedEmailTemplate updateData) {
        AssignedEmailTemplate assignment = assignedEmailTemplateRepository.findById(id).orElse(null);
        if (assignment != null) {
            assignment.setDay(updateData.getDay());
            assignedEmailTemplateRepository.save(assignment);
            return ResponseEntity.ok("Day updated successfully");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    @DeleteMapping("/assigned/{id}")
    public ResponseEntity<String> deleteAssignedEmailTemplate(@PathVariable Long id) {
        try {
            emailTemplateService.deleteAssignedEmailTemplateById(id);
            return ResponseEntity.ok("Assigned email template deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEmailTemplate(@PathVariable Long id) {
        try {
            emailTemplateService.deleteEmailTemplateById(id);
            return ResponseEntity.ok("Email template deleted successfully!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
