import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileDetailsComponent } from './profile-details/profile-details.component';



@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ProfileDetailsComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    LoginComponent,
    RegisterComponent,
    ProfileDetailsComponent
  ]
})
export class UserModule { }
