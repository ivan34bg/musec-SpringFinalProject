import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-birthday-change-view',
  templateUrl: './birthday-change-view.component.html',
  styleUrls: ['./birthday-change-view.component.scss']
})
export class BirthdayChangeViewComponent implements OnInit {
  newBirthday = new Date();
  oldPassword = '';
  isBirthdayValid = true;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  onSubmit(){
    this.userService.changeBirthday(new Date(this.newBirthday).toISOString().substring(0, 10), this.oldPassword);
  }

  dateChecker(value: Date){
    let dateToday = new Date();
    let chosenDate = new Date(value);
    if(
      chosenDate.getDate() >= dateToday.getDate() && 
      chosenDate.getMonth() >= dateToday.getMonth() && 
      chosenDate.getDay() >= dateToday.getDay()){
      this.isBirthdayValid = false;
    }
    else this.isBirthdayValid = true;
  }
}
