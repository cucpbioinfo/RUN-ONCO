import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ViewBiospecimenComponent } from "./view-biospecimen.component";

describe("ViewBiospecimenComponent", () => {
    let component: ViewBiospecimenComponent;
    let fixture: ComponentFixture<ViewBiospecimenComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ViewBiospecimenComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ViewBiospecimenComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
