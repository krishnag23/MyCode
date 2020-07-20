import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { HeaderquestionComponent } from './headerquestion.component';

describe('HeaderquestionComponent', () => {
  let component: HeaderquestionComponent;
  let fixture: ComponentFixture<HeaderquestionComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ HeaderquestionComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(HeaderquestionComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
