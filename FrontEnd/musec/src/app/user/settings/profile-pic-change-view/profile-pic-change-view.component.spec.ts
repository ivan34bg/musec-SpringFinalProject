import { ComponentFixture, TestBed } from '@angular/core/testing';

import { ProfilePicChangeViewComponent } from './profile-pic-change-view.component';

describe('ProfilePicChangeViewComponent', () => {
  let component: ProfilePicChangeViewComponent;
  let fixture: ComponentFixture<ProfilePicChangeViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ ProfilePicChangeViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ProfilePicChangeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
