import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { EmailTemplateComponent } from './components/email-template/email-template.component';
import { FileUploadComponent } from './components/file-upload/file-upload.component';
import { FileDataComponent } from './components/file-data/file-data.component';
import { AdminHomeComponent } from './components/admin-home/admin-home.component';

const routes: Routes = [
  {
    path: 'admin-home',
    component: AdminHomeComponent,
    children: [
      { path: 'emailTemp', component: EmailTemplateComponent },
      { path: 'new', component: FileUploadComponent },
      { path: 'dynamicData', component: FileDataComponent },
    ],
  },
  { path: '', redirectTo: '/admin-home', pathMatch: 'full' },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
})
export class AppRoutingModule {}
