import { Component } from '@angular/core';
import { EmailTemplate, EmailTemplateService } from '../../services/email-template.service';

@Component({
  selector: 'app-email-template',
  templateUrl: './email-template.component.html',
  styleUrls: ['./email-template.component.css']
})
export class EmailTemplateComponent {
  selectedFile?: File;
  message = '';
  subject: string = '';
  body: string = '';
  templateId: number = 0;
  emailTemplates: EmailTemplate[] = [];

  constructor(private emailTemplateService: EmailTemplateService) {}

  ngOnInit(): void {
    this.loadEmailTemplates();
  }

  // Method to handle file selection
  onFileSelected(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  // Method to upload the template and file
  onUpload(): void {
    const formData = new FormData();
    formData.append('subject', this.subject);
    formData.append('body', this.body);

    if (this.selectedFile) {
      formData.append('attachment', this.selectedFile, this.selectedFile.name);
    }

    this.emailTemplateService.uploadTemplate(formData);
  }

  // Synchronously load email templates
  loadEmailTemplates(): void {
    this.emailTemplates = this.emailTemplateService.getEmailTemplates();
  }

  // Method to download an attachment
  onDownloadAttachment(templateId: number): void {
    if (templateId) {
      this.emailTemplateService.downloadAttachment(templateId);
    } else {
      this.message = 'Please provide a valid template ID.';
    }
  }


  deleteTemplate(id: number): void {
    if (confirm('Are you sure you want to delete this template?')) {
      try{
        this.emailTemplateService.deleteTemplate(id);
        alert('Template deleted successfully!');
        this.loadEmailTemplates();
      }catch (error) {
                alert('Failed to delete the template. Please try again later.');
                console.error(error);
              }

      }
     
    }

  
}
