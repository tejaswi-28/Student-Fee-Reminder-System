import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class FileDataService {
  private apiUrl = 'http://localhost:8080/api/files'; // Backend URL
  private baseUrl = 'http://localhost:8080/EmailTemplates'; // Backend URL
  private emailLogUrl = 'http://localhost:8080/api/emails/email-log';
  private emailUrl = 'http://localhost:8080/api/emails';
  constructor() {}

  getFileNames(): string[] {
    const request = new XMLHttpRequest();
    request.open('GET', `${this.apiUrl}/names`, false); // `false` makes it synchronous
    request.send();

    if (request.status === 200) {
      return JSON.parse(request.responseText);
    } else {
      throw new Error(`Failed to fetch file names: ${request.statusText}`);
    }
  }

  getFileData(fileName: string): any[] {
    const request = new XMLHttpRequest();
    request.open('GET', `${this.apiUrl}/data/${fileName}`, false);
    request.send();

    if (request.status === 200) {
      return JSON.parse(request.responseText);
    } else {
      throw new Error(`Failed to fetch file data: ${request.statusText}`);
    }
  }

  assignTemplate(assignment: any): any {
    const request = new XMLHttpRequest();
    request.open('POST', `${this.baseUrl}/assign`, false);
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(JSON.stringify(assignment));

    if (request.status === 200 || request.status === 201) {
      return JSON.parse(request.responseText);
    } else {
      throw new Error(`Failed to assign template: ${request.statusText}`);
    }
  }

  getAssignedTemplates(): any[] {
    const request = new XMLHttpRequest();
    request.open('GET', `${this.baseUrl}/assigned`, false);
    request.send();

    if (request.status === 200) {
      return JSON.parse(request.responseText);
    } else {
      throw new Error(`Failed to fetch assigned templates: ${request.statusText}`);
    }
  }

  updateRowFlag(id: number, flag: number): void {
    const request = new XMLHttpRequest();
    request.open('PUT', `${this.baseUrl}/updateFlag/${id}`, false);
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(JSON.stringify({ flag }));

    if (request.status !== 200) {
      throw new Error(`Failed to update row flag: ${request.statusText}`);
    }
  }

  updateAssignmentDay(id: number, day: number): void {
    const request = new XMLHttpRequest();
    request.open('PUT', `${this.baseUrl}/updateDay/${id}`, false);
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(JSON.stringify({ day }));

    if (request.status !== 200) {
      throw new Error(`Failed to update assignment day: ${request.statusText}`);
    }
  }

  sendEmails(emailData: any[]): void {
    const request = new XMLHttpRequest();
    request.open('POST', `${this.emailUrl}/send`, false);
    request.setRequestHeader('Content-Type', 'application/json');
    request.send(JSON.stringify({ emailData }));

    if (request.status !== 200) {
      throw new Error(`Failed to send emails: ${request.statusText}`);
    }
  }

  getEmailLogs(): any[] {
    const request = new XMLHttpRequest();
    request.open('GET', this.emailLogUrl, false);
    request.send();

    if (request.status === 200) {
      return JSON.parse(request.responseText);
    } else {
      throw new Error(`Failed to fetch email logs: ${request.statusText}`);
    }
  }

  deleteAssignedTemplate(id: number): void {
    const request = new XMLHttpRequest();
    request.open('DELETE', `${this.baseUrl}/assigned/${id}`, false);
    request.send();
  
    if (request.status !== 200) {
      throw new Error(`Failed to delete assigned template: ${request.statusText}`);
    }
  }
  
}
