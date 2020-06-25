import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DynamicContentSearchComponent } from './dynamic-content-search.component';

describe('DynamicContentSearchComponent', () => {
  let component: DynamicContentSearchComponent;
  let fixture: ComponentFixture<DynamicContentSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DynamicContentSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DynamicContentSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
