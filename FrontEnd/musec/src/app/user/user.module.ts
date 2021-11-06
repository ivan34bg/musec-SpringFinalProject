import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { LoginComponent } from './login/login.component';
import { RegisterComponent } from './register/register.component';
import { ProfileDetailsComponent } from './profile-details/profile-details.component';
import { ProfileSettingsComponent } from './profile-settings/profile-settings.component';
import { Settings } from '@vime/angular';



@NgModule({
  declarations: [
    LoginComponent,
    RegisterComponent,
    ProfileDetailsComponent,
    ProfileSettingsComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    LoginComponent,
    RegisterComponent,
    ProfileDetailsComponent,
    ProfileSettingsComponent
  ]
})
export class UserModule { }
