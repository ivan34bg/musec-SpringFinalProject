import { ComponentFixture, TestBed } from '@angular/core/testing';

import { MainChangeViewComponent } from './main-change-view.component';

describe('MainChangeViewComponent', () => {
  let component: MainChangeViewComponent;
  let fixture: ComponentFixture<MainChangeViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ MainChangeViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(MainChangeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
