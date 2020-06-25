import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { SurvivalFollowupFormModalComponent } from './survival-followup-form-modal.component';

describe('SurvivalFollowupFormModalComponent', () => {
  let component: SurvivalFollowupFormModalComponent;
  let fixture: ComponentFixture<SurvivalFollowupFormModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ SurvivalFollowupFormModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(SurvivalFollowupFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
