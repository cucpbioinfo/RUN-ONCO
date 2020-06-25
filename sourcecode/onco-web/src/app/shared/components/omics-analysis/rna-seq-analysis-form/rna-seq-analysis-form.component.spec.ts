import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { RnaSeqAnalysisFormComponent } from './rna-seq-analysis-form.component';

describe('RnaSeqAnalysisFormComponent', () => {
  let component: RnaSeqAnalysisFormComponent;
  let fixture: ComponentFixture<RnaSeqAnalysisFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ RnaSeqAnalysisFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(RnaSeqAnalysisFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
