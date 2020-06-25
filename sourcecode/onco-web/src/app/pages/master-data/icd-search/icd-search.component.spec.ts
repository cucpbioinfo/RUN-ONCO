import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { IcdSearchComponent } from "./icd-search.component";

describe("IcdSearchComponent", () => {
    let component: IcdSearchComponent;
    let fixture: ComponentFixture<IcdSearchComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [IcdSearchComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(IcdSearchComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
