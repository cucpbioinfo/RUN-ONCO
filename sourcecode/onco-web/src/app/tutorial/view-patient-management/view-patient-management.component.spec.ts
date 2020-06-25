import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewPatientManagementComponent } from './view-patient-management.component';

describe('ViewPatientManagementComponent', () => {
  let component: ViewPatientManagementComponent;
  let fixture: ComponentFixture<ViewPatientManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewPatientManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewPatientManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
