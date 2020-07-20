import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { NooptionsComponent } from './nooptions.component';

describe('NooptionsComponent', () => {
  let component: NooptionsComponent;
  let fixture: ComponentFixture<NooptionsComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ NooptionsComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(NooptionsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
