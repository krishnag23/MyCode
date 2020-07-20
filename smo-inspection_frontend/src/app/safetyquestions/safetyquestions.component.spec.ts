import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SafetyquestionsComponent } from './safetyquestions.component';

describe('SafetyquestionsComponent', () => {
  let component: SafetyquestionsComponent;
  let fixture: ComponentFixture<SafetyquestionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SafetyquestionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SafetyquestionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
