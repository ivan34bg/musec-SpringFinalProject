import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UsernameChangeViewComponent } from './username-change-view.component';

describe('UsernameChangeViewComponent', () => {
  let component: UsernameChangeViewComponent;
  let fixture: ComponentFixture<UsernameChangeViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UsernameChangeViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UsernameChangeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
