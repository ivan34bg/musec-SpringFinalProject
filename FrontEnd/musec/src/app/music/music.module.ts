import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlbumViewComponent } from './album-view/album-view.component';
import { AlbumAddComponent } from './album-add/album-add.component';
import { PlaylistViewComponent } from './playlist-view/playlist-view.component';
import { PlaylistAddComponent } from './playlist-add/playlist-add.component';
import { RouterModule } from '@angular/router';
import { SingleViewComponent } from './single-view/single-view.component';
import { FormsModule } from '@angular/forms';
import { SingleAddComponent } from './single-add/single-add.component';
import { QueueComponent } from './queue/queue.component';
import { GenreComponent } from './genre/genre.component';



@NgModule({
  declarations: [
    AlbumViewComponent,
    AlbumAddComponent,
    PlaylistViewComponent,
    PlaylistAddComponent,
    SingleViewComponent,
    SingleAddComponent,
    QueueComponent,
    GenreComponent
  ],
  imports: [
    CommonModule,
    RouterModule,
    FormsModule
  ],
  exports: [
    AlbumViewComponent,
    AlbumAddComponent,
    PlaylistViewComponent,
    PlaylistAddComponent
  ]
})
export class MusicModule { }
