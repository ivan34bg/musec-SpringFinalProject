import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './nav-logged-in/nav.component';
import { PlayerComponent } from './player/player.component';
import { HomeLoggedComponent } from './home-logged/home-logged.component';
import { RouterModule } from '@angular/router';
import { NavLoggedOutComponent } from './nav-logged-out/nav-logged-out.component';
import { FormsModule } from '@angular/forms';
import { SearchComponent } from './search/search.component';



@NgModule({
  declarations: [
    NavComponent,
    PlayerComponent,
    HomeLoggedComponent,
    NavLoggedOutComponent,
    SearchComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule
  ],
  exports: [
    NavComponent,
    PlayerComponent,
    HomeLoggedComponent,
    NavLoggedOutComponent
  ]
})
export class CoreModule { }
