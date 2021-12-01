import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PasswordChangeViewComponent } from './password-change-view/password-change-view.component';
import { UsernameChangeViewComponent } from './username-change-view/username-change-view.component';
import { EmailChangeViewComponent } from './email-change-view/email-change-view.component';
import { MainChangeViewComponent } from './main-change-view/main-change-view.component';
import { RouterModule } from '@angular/router';
import { FullNameChangeViewComponent } from './full-name-change-view/full-name-change-view.component';
import { BirthdayChangeViewComponent } from './birthday-change-view/birthday-change-view.component';
import { FormsModule } from '@angular/forms';



@NgModule({
  declarations: [
    PasswordChangeViewComponent,
    UsernameChangeViewComponent,
    EmailChangeViewComponent,
    MainChangeViewComponent,
    FullNameChangeViewComponent,
    BirthdayChangeViewComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule
  ],
  exports: [
    PasswordChangeViewComponent,
    UsernameChangeViewComponent,
    EmailChangeViewComponent
  ]
})
export class SettingsModule { }
