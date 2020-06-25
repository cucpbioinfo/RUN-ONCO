import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IcdOFormModalComponent } from './icd-o-form-modal.component';

describe('IcdOFormModalComponent', () => {
  let component: IcdOFormModalComponent;
  let fixture: ComponentFixture<IcdOFormModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IcdOFormModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IcdOFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
