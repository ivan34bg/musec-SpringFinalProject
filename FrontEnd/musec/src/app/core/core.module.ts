import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { NavComponent } from './nav/nav.component';
import { PlayerComponent } from './player/player.component';
import { AngMusicPlayerModule } from 'ang-music-player';



@NgModule({
  declarations: [
    NavComponent,
    PlayerComponent
  ],
  imports: [
    AngMusicPlayerModule,
    CommonModule
  ],
  exports: [
    NavComponent,
    PlayerComponent
  ]
})
export class CoreModule { }
