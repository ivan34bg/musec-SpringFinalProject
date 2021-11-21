import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoggedInGuardGuard } from './core/guards/logged-in-guard.guard';
import { LoggedOutGuardGuard } from './core/guards/logged-out-guard.guard';
import { HomeLoggedComponent } from './core/home-logged/home-logged.component';
import { LoginComponent } from './user/login/login.component';
import { ProfileDetailsComponent } from './user/profile-details/profile-details.component';
import { ProfileSettingsComponent } from './user/profile-settings/profile-settings.component';
import { RegisterComponent } from './user/register/register.component';

const routes: Routes = [
  {
    path: '',
    pathMatch: 'full',
    redirectTo: '/browse'
  },
  {
    path: 'login',
    component: LoginComponent,
   // canActivate: [LoggedOutGuardGuard]
  },
  {
    path: 'register',
    component: RegisterComponent,
    //canActivate: [LoggedOutGuardGuard]
  },
  {
    path: 'browse',
    component: HomeLoggedComponent,
    //canActivate: [LoggedInGuardGuard]
  },
  {
    path: 'profile-settings',
    component: ProfileSettingsComponent
  },
  {
    path: 'profile-details',
    component: ProfileDetailsComponent
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  providers: [LoggedInGuardGuard]
})
export class AppRoutingModule { }
