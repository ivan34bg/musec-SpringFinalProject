import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlbumViewComponent } from './album-view/album-view.component';
import { AlbumAddComponent } from './album-add/album-add.component';
import { PlaylistViewComponent } from './playlist-view/playlist-view.component';
import { PlaylistAddComponent } from './playlist-add/playlist-add.component';
import { RouterModule } from '@angular/router';
import { SingleViewComponent } from './single-view/single-view.component';



@NgModule({
  declarations: [
    AlbumViewComponent,
    AlbumAddComponent,
    PlaylistViewComponent,
    PlaylistAddComponent,
    SingleViewComponent
  ],
  imports: [
    CommonModule,
    RouterModule
  ],
  exports: [
    AlbumViewComponent,
    AlbumAddComponent,
    PlaylistViewComponent,
    PlaylistAddComponent
  ]
})
export class MusicModule { }
