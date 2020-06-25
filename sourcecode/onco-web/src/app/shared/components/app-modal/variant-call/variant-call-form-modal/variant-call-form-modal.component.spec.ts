import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { VariantCallFormModalComponent } from "./variant-call-form-modal.component";

describe("VariantCallFormModalComponent", () => {
    let component: VariantCallFormModalComponent;
    let fixture: ComponentFixture<VariantCallFormModalComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [VariantCallFormModalComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(VariantCallFormModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
