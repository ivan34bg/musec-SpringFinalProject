import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlbumViewComponent } from './album-view/album-view.component';
import { AlbumAddComponent } from './album-add/album-add.component';
import { PlaylistViewComponent } from './playlist-view/playlist-view.component';
import { PlaylistAddComponent } from './playlist-add/playlist-add.component';



@NgModule({
  declarations: [
    AlbumViewComponent,
    AlbumAddComponent,
    PlaylistViewComponent,
    PlaylistAddComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    AlbumViewComponent,
    AlbumAddComponent,
    PlaylistViewComponent,
    PlaylistAddComponent
  ]
})
export class MusicModule { }
