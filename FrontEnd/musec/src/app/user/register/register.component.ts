import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
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
  isWorking: boolean = false;

  isBirthdateValid = false;
  arePasswordsTheSame: boolean = true;

  constructor(private userService: UserService, private router: Router) { }

  ngOnInit(): void {
  }

  onSubmit(){
    if(!this.username.trim() || !this.fullName.trim() || !this.email.trim() || !this.password.trim() || !this.confirmPassword.trim()){
      window.alert("Everything is required")
    }
    else{
      this.isWorking = true;
      this.userService.registerUser(this.username, this.fullName, this.email, this.password, new Date(this.birthdayDate).toISOString().substring(0, 10)).subscribe(
        response => {
          this.router.navigate(["login"]);
        },
        error => {
          this.isWorking = false;
          window.alert(error.error)
        }
      )
    }
    
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
    if(currentDate.getTime() < valueDate.getTime()){
        this.isBirthdateValid = false;
    }
    else{
      this.isBirthdateValid = true;
    }
  }

}
