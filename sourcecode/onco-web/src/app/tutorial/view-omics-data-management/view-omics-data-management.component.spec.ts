import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ViewOmicsDataManagementComponent } from './view-omics-data-management.component';

describe('ViewOmicsDataManagementComponent', () => {
  let component: ViewOmicsDataManagementComponent;
  let fixture: ComponentFixture<ViewOmicsDataManagementComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ViewOmicsDataManagementComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ViewOmicsDataManagementComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
