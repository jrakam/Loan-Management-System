import { Component, ChangeDetectorRef } from '@angular/core';
import { HttpClient, HttpErrorResponse, HttpEventType } from '@angular/common/http';

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
  styleUrls: ['./file-upload.component.css']
})
export class FileUploadComponent {
  fileUploads: FileUpload[] = Array.from({ length: 5 }, () => ({
    file: null, type: '', error: '', progress: 0, status: 'pending'
  }));
  uploadResponse: string = '';
  overallProgress: number = 0;

  constructor(private http: HttpClient, private cdr: ChangeDetectorRef) {}

  onFileSelected(event: Event, index: number) {
    const element = event.currentTarget as HTMLInputElement;
    let fileList: FileList | null = element.files;
    if (fileList) {
      const file: File = fileList[0];
      this.fileUploads[index].file = file;
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


  // onUpload() {
  //   const totalFiles = this.fileUploads.filter(fu => fu.file && fu.type).length;
  //   this.overallProgress = 0; // Reset overall progress at the start of the upload process
  
  //   this.fileUploads.forEach((fileUpload, index) => {
  //     if (fileUpload.file && fileUpload.type) {
  //       const formData = new FormData();
  //       formData.append('file', fileUpload.file);
  //       formData.append('type', fileUpload.type);
  
  //       this.http.post('your-upload-url', formData, {
  //         reportProgress: true,
  //         observe: 'events'
  //       }).subscribe(event => {
  //         if (event.type === HttpEventType.UploadProgress && event.total) {
  //           const progress = Math.round(100 * event.loaded / event.total);
  //           this.fileUploads[index].progress = progress; // Now 'progress' is correctly defined
  //           this.calculateOverallProgress();
  //         } else if (event.type === HttpEventType.Response) {
  //           // File upload success
  //           this.fileUploads[index].status = 'success';
  //           this.fileUploads[index].progress = 100; // Ensure progress is set to 100% on success
  //           this.calculateOverallProgress();
  //         }
  //       }, (error: HttpErrorResponse) => {
  //         // File upload error
  //         this.fileUploads[index].status = 'error';
  //         this.fileUploads[index].error = `File ${index + 1} upload failed: ${error.message}`;
  //         this.calculateOverallProgress();
  //         this.cdr.detectChanges();
  //       });
  //     } else {
  //       fileUpload.error = 'Please select a file and type for upload.';
  //       this.cdr.detectChanges();
  //     }
  //   });
  // }

  onUpload() {
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


  
}
