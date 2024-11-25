import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class EmailLogService {

  private apiUrl = 'http://localhost:8080/api/emails/email-log'; // Update this URL if needed

  constructor(private http: HttpClient) { }

  // Fetch email logs from the API
  getEmailLogs(): Observable<any[]> {
    return this.http.get<any[]>(this.apiUrl);
  }
}
