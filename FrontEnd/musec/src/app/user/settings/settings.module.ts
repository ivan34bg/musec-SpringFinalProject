import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PasswordChangeViewComponent } from './password-change-view/password-change-view.component';
import { UsernameChangeViewComponent } from './username-change-view/username-change-view.component';
import { EmailChangeViewComponent } from './email-change-view/email-change-view.component';



@NgModule({
  declarations: [
    PasswordChangeViewComponent,
    UsernameChangeViewComponent,
    EmailChangeViewComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    PasswordChangeViewComponent,
    UsernameChangeViewComponent,
    EmailChangeViewComponent
  ]
})
export class SettingsModule { }
