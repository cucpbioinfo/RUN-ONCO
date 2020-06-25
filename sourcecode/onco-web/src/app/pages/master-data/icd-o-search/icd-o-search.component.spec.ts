import { async, ComponentFixture, TestBed } from '@angular/core/testing';

import { IcdOSearchComponent } from './icd-o-search.component';

describe('IcdOSearchComponent', () => {
  let component: IcdOSearchComponent;
  let fixture: ComponentFixture<IcdOSearchComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ IcdOSearchComponent ]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(IcdOSearchComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
