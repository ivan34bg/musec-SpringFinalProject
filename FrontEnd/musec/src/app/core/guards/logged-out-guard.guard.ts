import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { from, Observable, of } from 'rxjs';
import { UserService } from 'src/app/user.service';
import { switchMap } from 'rxjs/operators'

@Injectable({
  providedIn: 'root'
})
export class LoggedOutGuardGuard implements CanActivate {
  constructor(private userService: UserService, private router: Router){}
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean | UrlTree> | Promise<boolean | UrlTree> | boolean | UrlTree {
      this.userService.serverIsUserLogged().subscribe(
        response => {
          this.router.navigate(["/browse"]);
          return false;
        }
      );
      return true;
  }
  
}
