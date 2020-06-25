import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClinicalDataSearchComponent } from './clinical-data-search.component';

describe('ClinicalDataSearchComponent', () => {
  let component: ClinicalDataSearchComponent;
  let fixture: ComponentFixture<ClinicalDataSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClinicalDataSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClinicalDataSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
