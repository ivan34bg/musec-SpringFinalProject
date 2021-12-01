import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-username-change-view',
  templateUrl: './username-change-view.component.html',
  styleUrls: ['./username-change-view.component.scss']
})
export class UsernameChangeViewComponent implements OnInit {
  password = '';
  username = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.userService.changeUsername(this.username, this.password);
  }
}
