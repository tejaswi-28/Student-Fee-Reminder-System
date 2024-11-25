import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  private apiUrl = 'http://localhost:8080/api/files/upload';

  constructor(private http: HttpClient) {}

  uploadFile(file: File, successCallback: (message: string) => void, errorCallback: (message: string) => void): void {
    const formData = new FormData();
    formData.append('file', file, file.name);

    this.http.post<{ message: string }>(this.apiUrl, formData).subscribe({
      next: (response) => successCallback(response.message),
      error: (error) => {
        const errorMessage =
          error?.error?.message || 'An unexpected error occurred.';
        errorCallback(errorMessage);
      }
    });
  }
}
