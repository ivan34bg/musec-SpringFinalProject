import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './nav/nav.component';
import { PlayerComponent } from './player/player.component';
import { AngMusicPlayerModule } from 'ang-music-player';
import { HomeLoggedComponent } from './home-logged/home-logged.component';



@NgModule({
  declarations: [
    NavComponent,
    PlayerComponent,
    HomeLoggedComponent
  ],
  imports: [
    AngMusicPlayerModule,
    CommonModule
  ],
  exports: [
    NavComponent,
    PlayerComponent,
    HomeLoggedComponent
  ]
})
export class CoreModule { }
