import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from './auth.service'; // Update the import path as necessary

@Injectable({
  providedIn: 'root'
})
export class FileUploadService {
  private baseUrl = 'http://localhost:8080/api/files';

  constructor(private http: HttpClient, private authService: AuthService) {}

  uploadFile(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);

    const encodedCredentials = this.authService.getEncodedCredentials();
    if (!encodedCredentials) {
      throw new Error('Authentication credentials not available');
    }

    const headers = new HttpHeaders({
      'Authorization': `Basic ${encodedCredentials}`
    });

    return this.http.post(`${this.baseUrl}/upload`, formData, {
      headers: headers,
      reportProgress: true,
      responseType: 'text'
    });
  }
}
