import { Component, OnInit } from '@angular/core';
import { playlistInfo } from 'src/app/models/playlist/playlistInfo.model';

@Component({
  selector: 'app-queue',
  templateUrl: './queue.component.html',
  styleUrls: ['./queue.component.scss']
})
export class QueueComponent implements OnInit {
  public playlistInfo = new playlistInfo();

  constructor() { }

  ngOnInit(): void {
  }

}
