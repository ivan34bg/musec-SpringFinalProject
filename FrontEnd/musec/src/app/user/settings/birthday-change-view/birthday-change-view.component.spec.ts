import { ComponentFixture, TestBed } from '@angular/core/testing';

import { BirthdayChangeViewComponent } from './birthday-change-view.component';

describe('BirthdayChangeViewComponent', () => {
  let component: BirthdayChangeViewComponent;
  let fixture: ComponentFixture<BirthdayChangeViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ BirthdayChangeViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(BirthdayChangeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
