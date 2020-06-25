import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { OmicsAnalysisFormComponent } from "./omics-analysis-form.component";

describe("OmicsAnalysisFormComponent", () => {
    let component: OmicsAnalysisFormComponent;
    let fixture: ComponentFixture<OmicsAnalysisFormComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [OmicsAnalysisFormComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(OmicsAnalysisFormComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
