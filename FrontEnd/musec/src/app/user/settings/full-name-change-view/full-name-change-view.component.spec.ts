import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FullNameChangeViewComponent } from './full-name-change-view.component';

describe('FullNameChangeViewComponent', () => {
  let component: FullNameChangeViewComponent;
  let fixture: ComponentFixture<FullNameChangeViewComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ FullNameChangeViewComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FullNameChangeViewComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
