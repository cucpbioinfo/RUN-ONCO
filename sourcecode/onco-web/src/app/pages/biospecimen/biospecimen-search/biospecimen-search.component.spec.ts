import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { BiospecimenSearchComponent } from "./biospecimen-search.component";

describe("BiospecimenSearchComponent", () => {
    let component: BiospecimenSearchComponent;
    let fixture: ComponentFixture<BiospecimenSearchComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [BiospecimenSearchComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(BiospecimenSearchComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
