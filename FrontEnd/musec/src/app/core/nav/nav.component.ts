import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-nav',
  templateUrl: './nav.component.html',
  styleUrls: ['./nav.component.scss']
})
export class NavComponent implements OnInit {

  isOpened:boolean = false;

  constructor() { }

  ngOnInit(): void {
  }

  openDropdown(event:Event){
    console.log(event.target);
  }
}
