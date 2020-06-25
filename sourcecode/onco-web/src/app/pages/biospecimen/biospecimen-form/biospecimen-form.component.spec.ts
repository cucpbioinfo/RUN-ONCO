import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { BiospecimenFormComponent } from './biospecimen-form.component';

describe('BiospecimenFormComponent', () => {
  let component: BiospecimenFormComponent;
  let fixture: ComponentFixture<BiospecimenFormComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ BiospecimenFormComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(BiospecimenFormComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
