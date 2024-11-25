package com.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.example.entity.AssignedEmailTemplate;
import com.example.entity.EmailTemplate;
import com.example.repository.AssignedEmailTemplateRepository;
import com.example.repository.EmailTemplateRepository;

import java.io.IOException;
import java.util.List;
@Service
public class EmailTemplateService {

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    @Autowired
    private AssignedEmailTemplateRepository assignedEmailTemplateRepository;

    public EmailTemplate saveEmailTemplate(String subject, String body, MultipartFile attachment) throws IOException {
        EmailTemplate emailTemplate = new EmailTemplate();
        emailTemplate.setSubject(subject);
        emailTemplate.setBody(body);

        if (attachment != null && !attachment.isEmpty()) {
            emailTemplate.setAttachmentName(attachment.getOriginalFilename());
            emailTemplate.setAttachmentType(attachment.getContentType());
            emailTemplate.setAttachmentData(attachment.getBytes());
        }

        return emailTemplateRepository.save(emailTemplate);
    }

    public EmailTemplate getEmailTemplateById(Long id) {
        return emailTemplateRepository.findById(id).orElse(null);
    }

    public AssignedEmailTemplate saveAssignment(AssignedEmailTemplate assignment) {
        boolean exists = assignedEmailTemplateRepository.existsByDataEmailAndTemplateId(
            assignment.getDataId(), assignment.getTemplateId()
        );

        if (exists) {
            throw new IllegalArgumentException("This template is already assigned to the specified data.");
        }

        return assignedEmailTemplateRepository.save(assignment);
    }

    public void deleteAssignedEmailTemplateById(Long id) {
        if (!assignedEmailTemplateRepository.existsById(id)) {
            throw new IllegalArgumentException("Assigned email template with ID " + id + " does not exist.");
        }
        assignedEmailTemplateRepository.deleteById(id);
    }


    public void deleteEmailTemplateById(Long id) {
        if (!emailTemplateRepository.existsById(id)) {
            throw new IllegalArgumentException("Email template with ID " + id + " does not exist.");
        }
        emailTemplateRepository.deleteById(id);
    }

}
