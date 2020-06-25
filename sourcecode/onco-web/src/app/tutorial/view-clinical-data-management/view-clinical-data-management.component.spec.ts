import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewClinicalDataManagementComponent } from './view-clinical-data-management.component';

describe('ViewClinicalDataManagementComponent', () => {
  let component: ViewClinicalDataManagementComponent;
  let fixture: ComponentFixture<ViewClinicalDataManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewClinicalDataManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewClinicalDataManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
