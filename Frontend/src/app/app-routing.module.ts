import { Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SignupComponent} from './signup/signup.component';
import { FileUploadComponent } from './file-upload/file-upload.component';


export const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  {path:'signup',component:SignupComponent},
  { path: 'file-upload', component: FileUploadComponent }
];