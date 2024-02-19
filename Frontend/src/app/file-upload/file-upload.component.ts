import { Component, ChangeDetectorRef } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpEventType } from '@angular/common/http';
import { HeaderComponent } from '../header/header.component';
import { FooterComponent } from '../footer/footer.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Observable } from 'rxjs';
import { FileUploadService} from '../fileUploadService';
import { AuthService } from '../auth.service';
import { fail } from 'assert';

import { forkJoin, of } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';


interface FileUpload {
  file: File | null;
  type: string;
  error?: string;
  progress?: number;
  status?: 'pending' | 'uploaded' | 'error';
  

}

@Component({
  selector: 'app-file-upload',
  templateUrl: './file-upload.component.html',
  styleUrls: ['./file-upload.component.css'],
  standalone:true,
  imports:[HeaderComponent,FooterComponent,CommonModule,FormsModule]
  
})
export class FileUploadComponent {
  
  fileUploads: FileUpload[] = Array.from({ length: 5 }, () => ({
    file: null, type: '', error: '', progress: 0, status: 'pending'
  }));
  uploadResponse: string = '';
  overallProgress: number = 0;
  showSuccessMessage: boolean=false;
  baseUrl = 'http://localhost:8080/api'; // Adjust according to your backend API's base URL
  

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef, private fileUploadService: FileUploadService,private authService: AuthService) {}



  onFileSelected(event: Event, index: number) {
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if (fileList) {
      this.fileUploads[index].file = fileList[0];
      this.fileUploads[index].progress = 0;
      this.fileUploads[index].status = 'pending';
      this.fileUploads[index].error = '';
      this.cdr.detectChanges();
    }
  }

  onTypeSelected(Type: string, index: number) {
   

    this.fileUploads[index].type = Type;
    // Reset the error message
    this.fileUploads[index].error = '';
    this.cdr.detectChanges();
  }

  isTypeSelected(type: string): boolean {
    return this.fileUploads.some(fu => fu.type === type);
  }
  availableTypes = ['State-Issued ID', 'Driver\'s License', 'Passport', 'Social Security Card', 'Proof of Address'];

  getAvailableTypes(index: number): string[] {
    const selectedTypes = this.fileUploads.map(fu => fu.type);
    return this.availableTypes.filter(type => !selectedTypes.includes(type) || this.fileUploads[index].type === type);
  }



  onUpload() {
    this.showSuccessMessage = false;
  this.fileUploads.forEach((fileUpload, index) => {
    if (fileUpload.file && fileUpload.type) {
      fileUpload.status = 'pending';
      const interval = setInterval(() => {
        // Check if progress is not undefined, or provide a default value
        const currentProgress = fileUpload.progress ?? 0;
        if (currentProgress < 100) {
          fileUpload.progress = currentProgress + 5;
          this.calculateOverallProgress();
        } else {
          fileUpload.status = 'uploaded';
          clearInterval(interval);
          this.calculateOverallProgress();
        }
        this.cdr.detectChanges();
      }, 200);
    } else {
      fileUpload.error = 'Please select a file and type for upload.';
      this.cdr.detectChanges();
    }
  });
}

  
  calculateOverallProgress() {
    const sumProgress = this.fileUploads.reduce((sum, current) => sum + (current.progress || 0), 0);
    this.overallProgress = sumProgress / this.fileUploads.length;
    this.cdr.detectChanges();
  }  
  
  // In your component class

allFilesChosen(): boolean {
  return this.fileUploads.every(fileUpload => fileUpload.file !== null);
}
uploadFiles(files: File[], customerId: number): Observable<any> {
  const formData: FormData = new FormData();
  
  files.forEach(file => {
      formData.append('files', file);
  });

  // Remove customerId if it's managed on the backend side
  return this.http.post(`${this.baseUrl}/upload`, formData, {
    reportProgress: true,
    responseType: 'text'
  });
}


// Assuming onSubmit is the method triggered when the form is submitted
// onSubmit() {
  
//   if (this.allFilesChosen()) {
    
//     this.fileUploads.forEach((fileUpload, index) => {
//       if (fileUpload.file && fileUpload.type) {
//         this.fileUploadService.uploadFile(fileUpload.file).subscribe({
//           next: (event) => {
//             if (event.type === HttpEventType.UploadProgress) {
//               fileUpload.progress = Math.round(100 * event.loaded / event.total);
//               console.log("success");
//             } else if (event.type === HttpEventType.Response) {
//               fileUpload.status = 'uploaded';
//               this.showSuccessMessage = true;
//               console.log("success");
//             }
//             this.cdr.detectChanges();
//           },
//           error: (error) => {
//             fileUpload.error = 'Upload failed';
//             fileUpload.status = 'error';
//             this.cdr.detectChanges();
//           },
//         });
//       } else {
//         fileUpload.error = 'Please select a file and type for upload.';
//         this.cdr.detectChanges();
//       }
//     });
//   } else {
//     console.error('Please ensure all files are selected before submitting.');
//   }
// }
onSubmit() {
  if (!this.allFilesChosen()) {
    console.error('Please ensure all files are selected before submitting.');
    return;
  }

  const uploadObservables = this.fileUploads.map(fileUpload => {
    if (fileUpload.file && fileUpload.type) {
      return this.fileUploadService.uploadFile(fileUpload.file).pipe(
        tap(event => {
          if (event.type === HttpEventType.UploadProgress) {
            fileUpload.progress = Math.round(100 * event.loaded / event.total);
          } else if (event.type === HttpEventType.Response) {
            fileUpload.status = 'uploaded';
          }
        }),
        catchError(error => {
          fileUpload.error = 'Upload failed';
          fileUpload.status = 'error';
          return of(null); // Handle error but continue with other uploads.
        })
      );
    }
    return of(null); // Skip if no file or type is selected.
  }).filter(obs => !!obs); // Remove nulls.

  forkJoin(uploadObservables).subscribe(results => {
    // All uploads are completed here.
    const allUploaded = this.fileUploads.every(fu => fu.status === 'uploaded');
    this.showSuccessMessage = allUploaded;
    if(allUploaded) {
      console.log('All files submitted successfully.');
    } else {
      console.error('Some files failed to upload.');
    }
    this.cdr.detectChanges();
  });
}



displaySuccessMessage() {
  console.log('All files submitted successfully.');
  // Display success message to the user, e.g., using a toast or modal
}



  
}