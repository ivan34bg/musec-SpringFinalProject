import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoggedInGuardGuard } from './core/guards/logged-in-guard.guard';
import { LoggedOutGuardGuard } from './core/guards/logged-out-guard.guard';
import { HomeLoggedComponent } from './core/home-logged/home-logged.component';
import { AlbumAddComponent } from './music/album-add/album-add.component';
import { AlbumViewComponent } from './music/album-view/album-view.component';
import { PlaylistAddComponent } from './music/playlist-add/playlist-add.component';
import { PlaylistViewComponent } from './music/playlist-view/playlist-view.component';
import { SingleAddComponent } from './music/single-add/single-add.component';
import { SingleViewComponent } from './music/single-view/single-view.component';
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
    canActivate: [LoggedOutGuardGuard]
  },
  {
    path: 'register',
    component: RegisterComponent,
    canActivate: [LoggedOutGuardGuard]
  },
  {
    path: 'browse',
    component: HomeLoggedComponent,
    canActivate: [LoggedInGuardGuard]
  },
  {
    path: 'profile-settings',
    component: ProfileSettingsComponent
  },
  {
    path: 'my-profile',
    component: ProfileDetailsComponent,
    canActivate: [LoggedInGuardGuard]
  },
  {
    path: 'album/create',
    component: AlbumAddComponent,
    canActivate: [LoggedInGuardGuard]
  },
  {
    path: 'album/:id',
    component: AlbumViewComponent,
    canActivate: [LoggedInGuardGuard]
  },  
  {
    path: 'playlist/create',
    component: PlaylistAddComponent,
    canActivate: [LoggedInGuardGuard]
  },
  {
    path: 'playlist/:id',
    component: PlaylistViewComponent,
    canActivate: [LoggedInGuardGuard]
  },
  {
    path: 'single/create',
    component: SingleAddComponent
  },
  {
    path: 'single/:id',
    component: SingleViewComponent,
    canActivate: [LoggedInGuardGuard]
  },
  {
    path: 'profile/:id',
    component: ProfileDetailsComponent,
    canActivate: [LoggedInGuardGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
