import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { PathoViewModalComponent } from './patho-view-modal.component';

describe('PathoViewModalComponent', () => {
  let component: PathoViewModalComponent;
  let fixture: ComponentFixture<PathoViewModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ PathoViewModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(PathoViewModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
