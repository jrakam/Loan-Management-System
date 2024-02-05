import { BrowserModule } from '@angular/platform-browser';
import { FormsModule } from '@angular/forms'; // Make sure FormsModule is imported
import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { HttpClientModule } from '@angular/common/http';
import { CommonModule } from '@angular/common';

import { AppComponent } from './app.component';
import { LoginComponent } from './login/login.component';
import { HomeComponent } from './home/home.component'; // Ensure this component is imported
import { routes } from './app-routing.module';
import { FileUploadComponent } from './file-upload/file-upload.component';
import { FooterComponent } from './footer/footer.component';
import { HeaderComponent } from './header/header.component'; // Ensure the routes are correctly defined


@NgModule({
  declarations: [
    AppComponent,
    
   
    // ... any other components this module uses
  ],
  imports: [
    BrowserModule,
    FormsModule,
    HttpClientModule,
    RouterModule.forRoot(routes, { useHash: true }), 
    CommonModule
    // ... other necessary imports
  ],
  providers: [],
  bootstrap: [AppComponent] // Don't forget to bootstrap the AppComponent
})
export class AppModule { }
