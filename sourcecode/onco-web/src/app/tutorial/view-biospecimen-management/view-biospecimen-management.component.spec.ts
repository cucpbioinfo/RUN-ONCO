import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewBiospecimenManagementComponent } from './view-biospecimen-management.component';

describe('ViewBiospecimenManagementComponent', () => {
  let component: ViewBiospecimenManagementComponent;
  let fixture: ComponentFixture<ViewBiospecimenManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewBiospecimenManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewBiospecimenManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
