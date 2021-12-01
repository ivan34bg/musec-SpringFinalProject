import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-full-name-change-view',
  templateUrl: './full-name-change-view.component.html',
  styleUrls: ['./full-name-change-view.component.scss']
})
export class FullNameChangeViewComponent implements OnInit {
  newFullName = '';
  oldPassword = '';

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.userService.changeFullName(this.newFullName, this.oldPassword);
  }
}
