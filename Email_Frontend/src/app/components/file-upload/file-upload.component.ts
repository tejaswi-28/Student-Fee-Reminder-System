import { Component } from '@angular/core';
import { FileUploadService } from '../../services/file-upload.service';

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent {
  selectedFile: File | null = null;
  message: string = '';

  constructor(private fileUploadService: FileUploadService) {}

  onFileSelect(event: any): void {
    this.selectedFile = event.target.files[0];
  }

  uploadFile(): void {
    if (!this.selectedFile) {
      this.message = 'Please select a file to upload.';
      return;
    }

    this.fileUploadService.uploadFile(
      this.selectedFile,
      (successMessage: string) => {
        this.message = successMessage; // Display success message
        alert(successMessage);
      },
      (errorMessage: string) => {
        this.message = errorMessage; // Display error message
        alert(errorMessage);
      }
    );
  }
}
