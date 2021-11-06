import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PasswordChangeViewComponent } from './password-change-view.component';

describe('PasswordChangeViewComponent', () => {
  let component: PasswordChangeViewComponent;
  let fixture: ComponentFixture<PasswordChangeViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PasswordChangeViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PasswordChangeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
