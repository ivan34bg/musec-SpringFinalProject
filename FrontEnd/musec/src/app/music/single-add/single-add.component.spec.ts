import { ComponentFixture, TestBed } from '@angular/core/testing';

import { SingleAddComponent } from './single-add.component';

describe('SingleAddComponent', () => {
  let component: SingleAddComponent;
  let fixture: ComponentFixture<SingleAddComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ SingleAddComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SingleAddComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
