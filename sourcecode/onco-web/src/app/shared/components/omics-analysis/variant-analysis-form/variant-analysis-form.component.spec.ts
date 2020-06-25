import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { VariantAnalysisFormComponent } from "./variant-analysis-form.component";

describe("VariantAnalysisFormComponent", () => {
    let component: VariantAnalysisFormComponent;
    let fixture: ComponentFixture<VariantAnalysisFormComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [VariantAnalysisFormComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(VariantAnalysisFormComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
