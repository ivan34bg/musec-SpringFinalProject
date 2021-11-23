import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { albumInfo } from 'src/app/models/albumInfo.model';
import { AlbumService } from 'src/app/services/album.service';

@Component({
  selector: 'app-album-view',
  templateUrl: './album-view.component.html',
  styleUrls: ['./album-view.component.scss']
})
export class AlbumViewComponent implements OnInit {
  public albumInfo: albumInfo = new albumInfo();
  public counter = 0;

  constructor(
    private router: Router, 
    private albumService: AlbumService, 
    private activatedRoute: ActivatedRoute
    ) { }

  ngOnInit(): void {
    this.albumService.requestAlbum(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        let album = JSON.parse(JSON.stringify(response));
        this.albumInfo.albumName = album.albumName;
        this.albumInfo.albumCreator = album.uploader;
        this.albumInfo.albumPicLink = album.albumPicLocation;
        this.albumInfo.albumSongs = album.songs;
        console.log(this.albumInfo.albumSongs);
      },
      error => {
        this.router.navigate(['/browse'])
        alert('Album cannot be found!');
      }
    );
  }

}
