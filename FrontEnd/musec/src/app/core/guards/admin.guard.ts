import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { UserService } from 'src/app/services/user.service';

@Injectable({
  providedIn: 'root'
})
export class AdminGuard implements CanActivate {
  constructor(private userService: UserService, private router: Router){}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
    this.userService.isLoggedUserAdmin().subscribe(
      response => {
        if(JSON.stringify(response) == "true"){
          return true;
        }
        else {
          this.router.navigate(['/browse'])
          return false;
        }
      },
      error => {
        this.router.navigate(['/browse'])
        return false;
      }
    );
    return true;
  }
}
