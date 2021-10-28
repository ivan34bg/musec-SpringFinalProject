import { ComponentFixture, TestBed } from '@angular/core/testing';

import { HomeLoggedComponent } from './home-logged.component';

describe('HomeLoggedComponent', () => {
  let component: HomeLoggedComponent;
  let fixture: ComponentFixture<HomeLoggedComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HomeLoggedComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(HomeLoggedComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
