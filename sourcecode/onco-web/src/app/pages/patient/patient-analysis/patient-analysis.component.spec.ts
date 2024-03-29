import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { PatientAnalysisComponent } from "./patient-analysis.component";

describe("PatientAnalysisComponent", () => {
    let component: PatientAnalysisComponent;
    let fixture: ComponentFixture<PatientAnalysisComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [PatientAnalysisComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(PatientAnalysisComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
