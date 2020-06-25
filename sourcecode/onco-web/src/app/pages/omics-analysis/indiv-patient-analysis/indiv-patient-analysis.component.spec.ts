import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { IndivPatientAnalysisComponent } from "./indiv-patient-analysis.component";

describe("IndivPatientAnalysisComponent", () => {
    let component: IndivPatientAnalysisComponent;
    let fixture: ComponentFixture<IndivPatientAnalysisComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [IndivPatientAnalysisComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(IndivPatientAnalysisComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
