import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

export interface EmailTemplate {
  id: number;
  name: string;
  subject: string;
  body: string;
  attachmentUrl: string;
}

@Injectable({
  providedIn: 'root'
})
export class EmailTemplateService {

  private baseUrl = 'http://localhost:8080/EmailTemplates';

  constructor(private http: HttpClient) {}

  // Upload the email template with attachment
  uploadTemplate(formData: FormData): void {
    const con = new XMLHttpRequest();
    con.open('POST', `${this.baseUrl}/create`, false);  // false for synchronous request
    con.send(formData);

    if (con.status === 200) {
      alert('Template uploaded successfully');
    } else {
      alert('Failed to upload template');
    }
  }

  // Download an attachment based on the email template ID
  downloadAttachment(id: number): void {
    const con = new XMLHttpRequest();
    con.open('GET', `${this.baseUrl}/attachment/${id}`, false);  // false for synchronous request
    con.responseType = 'blob';  // Expecting a blob response

    con.send();

    if (con.status === 200) {
      const blob = con.response;
      const link = document.createElement('a');
      link.href = URL.createObjectURL(blob);
      link.download = `attachment_${id}`;  // Provide a default name for the file
      link.click();
    } else {
      alert('Failed to download attachment');
    }
  }

  // Fetch a single email template by ID
  getEmailTemplate(id: number): EmailTemplate | null {
    const con = new XMLHttpRequest();
    con.open('GET', `${this.baseUrl}/${id}`, false);  // false for synchronous request
    con.send();

    if (con.status === 200) {
      return JSON.parse(con.responseText) as EmailTemplate;
    } else {
      alert('Failed to fetch email template');
      return null;
    }
  }

  // Fetch all email templates
  getEmailTemplates(): EmailTemplate[] {
    const con = new XMLHttpRequest();
    con.open('GET', `${this.baseUrl}/email-templates`, false);  // false for synchronous request
    con.send();

    if (con.status === 200) {
      return JSON.parse(con.responseText) as EmailTemplate[];
    } else {
      alert('Failed to fetch email templates');
      return [];
    }
  }

  deleteTemplate(id: number):void{
    const request = new XMLHttpRequest();
    request.open('DELETE', `${this.baseUrl}/delete/${id}`, false);
    request.send();
    if (request.status !== 200) {
      throw new Error(`Failed to delete assigned template: ${request.statusText}`);
    }
  }

}
