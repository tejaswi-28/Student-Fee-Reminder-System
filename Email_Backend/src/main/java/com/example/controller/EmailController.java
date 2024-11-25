package com.example.controller;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.entity.AssignedEmailTemplate;
import com.example.repository.AssignedEmailTemplateRepository;
import com.example.services.EmailService;
import com.example.services.EmailService.EmailLog;

@RestController
@RequestMapping("/api/emails")
@CrossOrigin(origins="http://localhost:4200")
public class EmailController {

    @Autowired
    private EmailService emailService;
    @Autowired
    private AssignedEmailTemplateRepository assignedEmailTemplateRepository;

    @PostMapping("/send")
    public ResponseEntity<String> sendEmails() {
        try {
            emailService.sendScheduledEmails();
            return ResponseEntity.ok("Emails sent successfully.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Failed to send emails: " + e.getMessage());
        }
    }
 // REST API to get the email log and next scheduled send date
    @GetMapping("/email-log")
    public List<EmailLog> getEmailLog() {
        List<AssignedEmailTemplate> templates = assignedEmailTemplateRepository.findByFlag(1);

        return templates.stream().map(template -> {
            Date nextSendDate = calculateNextSendDate(template);
            return new EmailLog(template.getDataEmail(), template.getLastSentDate(), nextSendDate);
        }).collect(Collectors.toList());
    }
    
    // Helper to calculate the next scheduled email date based on 'day' interval
    private Date calculateNextSendDate(AssignedEmailTemplate template) {
        if (template.getLastSentDate() == null) {
            return new Date(); // If never sent, next date is now
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(template.getLastSentDate());
        calendar.add(Calendar.DAY_OF_MONTH, template.getDay());
        return calendar.getTime();
    }

}
