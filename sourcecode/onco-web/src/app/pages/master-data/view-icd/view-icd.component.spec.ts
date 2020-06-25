import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ViewIcdComponent } from "./view-icd.component";

describe("ViewIcdComponent", () => {
    let component: ViewIcdComponent;
    let fixture: ComponentFixture<ViewIcdComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ViewIcdComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ViewIcdComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
