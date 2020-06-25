import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { VariantComparisonComponent } from "./variant-comparison.component";

describe("VariantComparisonComponent", () => {
    let component: VariantComparisonComponent;
    let fixture: ComponentFixture<VariantComparisonComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [VariantComparisonComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(VariantComparisonComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
