package com.example.services;

import com.example.entity.AssignedEmailTemplate;
import com.example.entity.EmailTemplate;
import com.example.repository.AssignedEmailTemplateRepository;
import com.example.repository.EmailTemplateRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.mail.util.ByteArrayDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private AssignedEmailTemplateRepository assignedEmailTemplateRepository;

    @Autowired
    private EmailTemplateRepository emailTemplateRepository;

    // Scheduled task to check and send emails every hour
    @Scheduled(cron = "0 0 * * * *") // Adjust cron as needed
    public void sendScheduledEmails() {
        List<AssignedEmailTemplate> templates = assignedEmailTemplateRepository.findByFlag(1);

        for (AssignedEmailTemplate assignedTemplate : templates) {
            if (shouldSendEmail(assignedTemplate)) {
                try {
                    sendEmail(assignedTemplate);
                    assignedTemplate.setLastSentDate(new Date()); // Update the last sent date
                    assignedEmailTemplateRepository.save(assignedTemplate); // Save update
                } catch (MessagingException e) {
                    System.err.println("Failed to send email: " + e.getMessage());
                }
            }
        }
    }

    // Method to determine if an email should be sent based on the 'day' interval
    private boolean shouldSendEmail(AssignedEmailTemplate template) {
        if (template.getLastSentDate() == null) return true; // Send if never sent before

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(template.getLastSentDate());
        calendar.add(Calendar.DAY_OF_MONTH, template.getDay()); // Add interval

        return new Date().after(calendar.getTime());
    }

    // Method to send the email using the template ID
    public void sendEmail(AssignedEmailTemplate assignedTemplate) throws MessagingException {
        EmailTemplate emailTemplate = emailTemplateRepository.findById(assignedTemplate.getTemplateId())
                .orElseThrow(() -> new IllegalArgumentException("Template not found"));

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(assignedTemplate.getDataEmail());
        helper.setSubject(emailTemplate.getSubject());
        helper.setText(emailTemplate.getBody(), true);

        if (emailTemplate.getAttachmentData() != null) {
            helper.addAttachment(emailTemplate.getAttachmentName(),
                    new ByteArrayDataSource(emailTemplate.getAttachmentData(), emailTemplate.getAttachmentType()));
        }

        mailSender.send(message);
    } 

    // Class to structure the response data for /email-log endpoint
    public static class EmailLog {
        private String emailSentTo;
        private Date lastSentDate;
        private Date nextSendDate;

        public EmailLog(String emailSentTo, Date lastSentDate, Date nextSendDate) {
            this.emailSentTo = emailSentTo;
            this.lastSentDate = lastSentDate;
            this.nextSendDate = nextSendDate;
        }

        public String getEmailSentTo() {
            return emailSentTo;
        }

        public Date getLastSentDate() {
            return lastSentDate;
        }

        public Date getNextSendDate() {
            return nextSendDate;
        }
    }
}
