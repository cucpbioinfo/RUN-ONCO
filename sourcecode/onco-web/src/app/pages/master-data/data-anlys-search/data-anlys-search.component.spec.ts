import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { DataAnlysSearchComponent } from './data-anlys-search.component';

describe('DataAnlysSearchComponent', () => {
  let component: DataAnlysSearchComponent;
  let fixture: ComponentFixture<DataAnlysSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ DataAnlysSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(DataAnlysSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
