import { Component, OnInit } from '@angular/core';
import { FileDataService } from '../../services/file-data.service';
import { EmailTemplateService } from '../../services/email-template.service';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-file-data',
  templateUrl: './file-data.component.html',
  styleUrls: ['./file-data.component.css']
})
export class FileDataComponent implements OnInit {
  fileNames: string[] = [];
  selectedFileName: string = '';
  fileData: any[] = [];
  emailTemplates: any[] = [];
  assignedTemplates: any[] = [];
  emailLogs: any;

  constructor(
    private fileDataService: FileDataService,
    private emailTemplateService: EmailTemplateService,
    private http: HttpClient
  ) {}

  ngOnInit(): void {
    this.loadFileNames();
    this.loadEmailTemplates();
    this.loadAssignedTemplates();
  }

  loadFileNames(): void {
    try {
      this.fileNames = this.fileDataService.getFileNames(); // Synchronously fetch file names
    } catch (error) {
      alert('Failed to load file names. Please try again later.');
      console.error(error);
    }
  }
  
  loadEmailTemplates(): void {
    try {
      this.emailTemplates = this.emailTemplateService.getEmailTemplates(); // Synchronously fetch email templates
    } catch (error) {
      alert('Failed to load email templates. Please try again later.');
      console.error(error);
    }
  }
  
  loadAssignedTemplates(): void {
    try {
      this.assignedTemplates = this.fileDataService.getAssignedTemplates(); // Synchronously fetch assigned templates
    } catch (error) {
      alert('Failed to load assigned templates. Please try again later.');
      console.error(error);
    }
  }
  
  onFileNameSelect(): void {
    if (this.selectedFileName) {
      try {
        this.fileData = this.fileDataService.getFileData(this.selectedFileName); // Synchronously fetch file data
      } catch (error) {
        alert('Failed to load file data. Please try again later.');
        console.error(error);
      }
    }
  }
  
  assignTemplate(row: any): void {
    const dataIdIndex = this.fileData[0]?.findIndex((header: string) => header.toLowerCase() === 'id');
    const emailIndex = this.fileData[0]?.findIndex((header: string) => header.toLowerCase() === 'email');
  
    if (dataIdIndex === -1 || emailIndex === -1 || !row.templateId) {
      alert('Select a template.');
      return;
    }
  
    const assignment = {
      dataId: row[dataIdIndex],
      templateId: row.templateId,
      fileName: this.selectedFileName,
      dataEmail: row[emailIndex],
      day: row.day,          // Day from frontend input (1-31)
      time: row.time         // Time from frontend input in "HH:mm" format
    };
  
    try {
      this.fileDataService.assignTemplate(assignment); // Synchronously assign template
      alert('Template assigned successfully!');
      this.loadAssignedTemplates();
    } catch (error) {
      alert('This Template is already assigned. Please try again.');
      console.error(error);
    }
  }
  
  toggleRowFlag(row: any): void {
    row.flag = row.flag === 1 ? 0 : 1;
  
    try {
      this.fileDataService.updateRowFlag(row.id, row.flag); // Synchronously update flag
      alert(`Flag updated`);
    } catch (error) {
      alert(`Failed to update flag for ID ${row.id}. Please try again later.`);
      console.error(error);
    }
  }
  
  // New method to save the day value
  saveDay(assignment: any): void {
    try {
      this.fileDataService.updateAssignmentDay(assignment.id, assignment.day); // Synchronously update day
      alert('Day saved successfully!');
      this.loadAssignedTemplates();
    } catch (error) {
      alert('Failed to save day. Please try again later.');
      console.error(error);
    }
  }
  
  // New method to send emails using the service
  sendEmails(): void {
    try {
      this.fileDataService.sendEmails(this.assignedTemplates); // Synchronously send emails
      alert('Emails sent successfully!');
    } catch (error) {
      alert('Failed to send emails. Please try again later.');
      console.error(error);
    }
  }
  
  // Method to assign lastSentDate and nextSendDate from emailLogs to assignedTemplates
  assignEmailLogData(): void {
    this.assignedTemplates.forEach((assignment) => {
      const emailLog = this.emailLogs.find((log: { emailSentTo: string }) => log.emailSentTo === assignment.dataEmail);
      if (emailLog) {
        assignment.lastSentDate = emailLog.lastSentDate;
        assignment.nextSendDate = emailLog.nextSendDate;
      }
    });
  }

  deleteTemplate(assignment: any): void {
    if (confirm(`Are you sure you want to delete the assigned template ?`)) {
      try {
        this.fileDataService.deleteAssignedTemplate(assignment.id); // Call the service method
        alert('Template deleted successfully!');
        this.loadAssignedTemplates(); // Refresh the table
      } catch (error) {
        alert('Failed to delete the assigned template. Please try again later.');
        console.error(error);
      }
    }
  }
  
}