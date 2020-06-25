import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { PatientVariantComparisonComponent } from "./patient-variant-comparison.component";

describe("PatientVariantComparisonComponent", () => {
    let component: PatientVariantComparisonComponent;
    let fixture: ComponentFixture<PatientVariantComparisonComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [PatientVariantComparisonComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(PatientVariantComparisonComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
