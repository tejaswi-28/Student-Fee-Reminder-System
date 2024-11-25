package com.example.entity;

import jakarta.persistence.*;
import java.util.Date;

@Entity
@Table(name = "assigned_email_template")
public class AssignedEmailTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String dataId;
    private Long templateId;
    private String fileName;
    private String dataEmail;
    private Integer flag = 0; // Flag to indicate whether to send email
    private Integer day; // Day interval for sending email

    @Temporal(TemporalType.TIMESTAMP)
    private Date lastSentDate;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDataId() { return dataId; }
    public void setDataId(String dataId) { this.dataId = dataId; }

    public Long getTemplateId() { return templateId; }
    public void setTemplateId(Long templateId) { this.templateId = templateId; }

    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }

    public String getDataEmail() { return dataEmail; }
    public void setDataEmail(String dataEmail) { this.dataEmail = dataEmail; }

    public Integer getFlag() { return flag; }
    public void setFlag(Integer flag) { this.flag = flag; }

    public Integer getDay() { return day; }
    public void setDay(Integer day) { this.day = day; }

    public Date getLastSentDate() { return lastSentDate; }
    public void setLastSentDate(Date lastSentDate) { this.lastSentDate = lastSentDate; }
}
