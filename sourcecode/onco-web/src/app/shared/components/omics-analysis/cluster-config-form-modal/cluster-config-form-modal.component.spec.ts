import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { ClusterConfigFormModalComponent } from './cluster-config-form-modal.component';

describe('ClusterConfigFormModalComponent', () => {
  let component: ClusterConfigFormModalComponent;
  let fixture: ComponentFixture<ClusterConfigFormModalComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ ClusterConfigFormModalComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ClusterConfigFormModalComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
