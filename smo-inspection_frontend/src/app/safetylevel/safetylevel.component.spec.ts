import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SafetylevelComponent } from './safetylevel.component';

describe('SafetylevelComponent', () => {
  let component: SafetylevelComponent;
  let fixture: ComponentFixture<SafetylevelComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SafetylevelComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SafetylevelComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
