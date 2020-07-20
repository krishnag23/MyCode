import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { QuestionmainComponent } from './questionmain.component';

describe('QuestionmainComponent', () => {
  let component: QuestionmainComponent;
  let fixture: ComponentFixture<QuestionmainComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ QuestionmainComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(QuestionmainComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
