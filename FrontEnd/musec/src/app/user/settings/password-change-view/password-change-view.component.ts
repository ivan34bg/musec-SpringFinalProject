import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-password-change-view',
  templateUrl: './password-change-view.component.html',
  styleUrls: ['./password-change-view.component.scss']
})
export class PasswordChangeViewComponent implements OnInit {
  newPassword = '';
  confirmNewPassword = '';
  oldPassword = '';

  doPasswordsMatch = true;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.userService.changePassword(this.newPassword, this.oldPassword);
  }

  newPasswordChecker(){
    if(this.newPassword != this.confirmNewPassword){
      this.doPasswordsMatch = false;
    }
    else this.doPasswordsMatch = true;
  }
}
