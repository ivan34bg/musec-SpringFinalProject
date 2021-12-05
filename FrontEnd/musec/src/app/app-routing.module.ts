import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ArtistGuard } from './core/guards/artist.guard';
import { LoggedInGuardGuard } from './core/guards/logged-in-guard.guard';
import { LoggedOutGuardGuard } from './core/guards/logged-out-guard.guard';
import { HomeLoggedComponent } from './core/home-logged/home-logged.component';
import { SearchComponent } from './core/search/search.component';
import { AlbumAddComponent } from './music/album-add/album-add.component';
import { AlbumViewComponent } from './music/album-view/album-view.component';
import { GenreComponent } from './music/genre/genre.component';
import { PlaylistAddComponent } from './music/playlist-add/playlist-add.component';
import { PlaylistViewComponent } from './music/playlist-view/playlist-view.component';
import { QueueComponent } from './music/queue/queue.component';
import { SingleAddComponent } from './music/single-add/single-add.component';
import { SingleViewComponent } from './music/single-view/single-view.component';
import { LoginComponent } from './user/login/login.component';
import { ProfileDetailsComponent } from './user/profile-details/profile-details.component';
import { ProfileSettingsComponent } from './user/profile-settings/profile-settings.component';
import { RegisterComponent } from './user/register/register.component';
import { BirthdayChangeViewComponent } from './user/settings/birthday-change-view/birthday-change-view.component';
import { EmailChangeViewComponent } from './user/settings/email-change-view/email-change-view.component';
import { FullNameChangeViewComponent } from './user/settings/full-name-change-view/full-name-change-view.component';
import { MainChangeViewComponent } from './user/settings/main-change-view/main-change-view.component';
import { PasswordChangeViewComponent } from './user/settings/password-change-view/password-change-view.component';
import { ProfilePicChangeViewComponent } from './user/settings/profile-pic-change-view/profile-pic-change-view.component';
import { UsernameChangeViewComponent } from './user/settings/username-change-view/username-change-view.component';

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
    component: ProfileSettingsComponent,
    canActivate: [LoggedInGuardGuard],
    children: [
      {
        path: '',
        component: MainChangeViewComponent,
        canActivate: [LoggedInGuardGuard]
      },
      {
        path: 'username',
        component: UsernameChangeViewComponent,
        canActivate: [LoggedInGuardGuard]
      },
      {
        path: 'email',
        component: EmailChangeViewComponent,
        canActivate: [LoggedInGuardGuard]
      },
      {
        path: 'password',
        component: PasswordChangeViewComponent,
        canActivate: [LoggedInGuardGuard]
      },
      {
        path: 'birthday',
        component: BirthdayChangeViewComponent,
        canActivate: [LoggedInGuardGuard]
      },
      {
        path: 'full-name',
        component: FullNameChangeViewComponent,
        canActivate: [LoggedInGuardGuard]
      },
      {
        path: 'profile-pic',
        component: ProfilePicChangeViewComponent,
        canActivate: [LoggedInGuardGuard]
      }
    ]
  },
  {
    path: 'my-profile',
    component: ProfileDetailsComponent,
    canActivate: [LoggedInGuardGuard]
  },
  {
    path: 'album/create',
    component: AlbumAddComponent,
    canActivate: [LoggedInGuardGuard, ArtistGuard],
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
    component: SingleAddComponent,
    canActivate: [LoggedInGuardGuard, ArtistGuard]
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
  },
  {
    path: 'queue',
    component: QueueComponent,
    canActivate: [LoggedInGuardGuard]
  },
  {
    path: 'search/:param',
    component: SearchComponent,
    canActivate: [LoggedInGuardGuard]
  },
  {
    path: 'genre/:id',
    component: GenreComponent,
    canActivate: [LoggedInGuardGuard]
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
