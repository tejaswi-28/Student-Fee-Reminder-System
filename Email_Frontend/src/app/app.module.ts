import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { RouterModule } from '@angular/router';
import { HttpClientModule, provideHttpClient, withFetch } from '@angular/common/http'; // Import necessary modules
import { FormsModule } from '@angular/forms';
import { AppComponent } from './app.component';
import { AppRoutingModule } from './app-routing.module';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { FileDataComponent } from './components/file-data/file-data.component';
import { FileUploadService } from './services/file-upload.service';
import { FileDataService } from './services/file-data.service';
import { EmailTemplateComponent } from './components/email-template/email-template.component';
import { AdminHomeComponent } from './components/admin-home/admin-home.component';

@NgModule({
  declarations: [
    AppComponent,
    FileUploadComponent,
    FileDataComponent,
    EmailTemplateComponent,
    AdminHomeComponent,
    
  ],
  imports: [
    BrowserModule,
    HttpClientModule,
    FormsModule,
    RouterModule,
    AppRoutingModule
  ],
  providers: [
    FileDataService,
    FileUploadService,
    provideHttpClient(withFetch()),
    
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
