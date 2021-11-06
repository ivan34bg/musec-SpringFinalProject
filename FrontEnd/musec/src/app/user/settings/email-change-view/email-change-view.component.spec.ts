import { ComponentFixture, TestBed } from '@angular/core/testing';

import { EmailChangeViewComponent } from './email-change-view.component';

describe('EmailChangeViewComponent', () => {
  let component: EmailChangeViewComponent;
  let fixture: ComponentFixture<EmailChangeViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ EmailChangeViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(EmailChangeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
