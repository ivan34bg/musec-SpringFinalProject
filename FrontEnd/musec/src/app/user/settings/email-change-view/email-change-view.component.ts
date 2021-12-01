import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-email-change-view',
  templateUrl: './email-change-view.component.html',
  styleUrls: ['./email-change-view.component.scss']
})
export class EmailChangeViewComponent implements OnInit {
  newEmail = '';
  oldPassword = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.userService.changeEmail(this.newEmail, this.oldPassword);
  }
}
