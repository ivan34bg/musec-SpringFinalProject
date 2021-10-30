import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { AlbumViewComponent } from './album-view/album-view.component';



@NgModule({
  declarations: [
    AlbumViewComponent
  ],
  imports: [
    CommonModule
  ],
  exports: [
    AlbumViewComponent
  ]
})
export class MusicModule { }
