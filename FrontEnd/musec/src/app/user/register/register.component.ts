import { Component, OnInit } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent implements OnInit {
  username = "";
  fullName = "";
  email = "";
  password = "";
  confirmPassword = "";
  birthdayDate = new Date();

  isBirthdateValid: boolean = true;
  arePasswordsTheSame: boolean = true;

  constructor(private userService: UserService) { }

  ngOnInit(): void {
  }

  onSubmit(){
    let form = new FormData;
    form.append('username', this.username);
    form.append('fullName', this.fullName);
    form.append('email', this.email);
    form.append('password', this.password);
    form.append('birthday', new Date(this.birthdayDate).toISOString().substring(0, 10));

    this.userService.registerUser(form);
  }

  passwordChecker(password: String, confirmPassword: String){
    if(password !== confirmPassword)
      this.arePasswordsTheSame = false;
    else
      this.arePasswordsTheSame = true;
  }

  dateChecker(value: Date){
    let currentDate = new Date();
    let valueDate = new Date(value);
    if(
      valueDate.getDate() >= currentDate.getDate() && 
      valueDate.getMonth() >= currentDate.getMonth() && 
      valueDate.getDay() >= currentDate.getDay()){
      this.isBirthdateValid = false;
    }
    else{
      this.isBirthdateValid = true;
    }
  }

}
