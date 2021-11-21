import { ComponentFixture, TestBed } from '@angular/core/testing';

import { NavLoggedOutComponent } from './nav-logged-out.component';

describe('NavLoggedOutComponent', () => {
  let component: NavLoggedOutComponent;
  let fixture: ComponentFixture<NavLoggedOutComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ NavLoggedOutComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavLoggedOutComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
