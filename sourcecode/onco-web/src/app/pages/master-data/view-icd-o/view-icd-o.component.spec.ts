import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ViewIcdOComponent } from "./view-icd-o.component";

describe("ViewIcdOComponent", () => {
    let component: ViewIcdOComponent;
    let fixture: ComponentFixture<ViewIcdOComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ViewIcdOComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ViewIcdOComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
