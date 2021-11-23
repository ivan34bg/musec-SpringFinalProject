import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { singleInfo } from 'src/app/models/single/singleInfo.model';
import { SingleService } from 'src/app/services/single.service';

@Component({
  selector: 'app-single-view',
  templateUrl: './single-view.component.html',
  styleUrls: ['./single-view.component.scss']
})
export class SingleViewComponent implements OnInit {
  public singleInfo: singleInfo = new singleInfo();

  constructor(
    private singleService: SingleService, 
    private router: Router, 
    private activatedRoute: ActivatedRoute) { }

  ngOnInit(): void {
    this.singleService.requestSingleById(this.activatedRoute.snapshot.params.id).subscribe(
      response => {
        let single = JSON.parse(JSON.stringify(response));
        this.singleInfo.singleName = single.singleName;
        this.singleInfo.singlePicLocation = single.singlePicLocation;
        this.singleInfo.song = single.song;
        this.singleInfo.uploader = single.uploader;
      },
      error => {
        alert("This single cannot be found");
        this.router.navigate(['/browse']);
      }
    );
  }

}
