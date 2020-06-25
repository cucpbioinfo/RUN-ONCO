import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ActionableVariantViewModalComponent } from "./actionable-variant-view-modal.component";

describe("ActionableVariantViewModalComponent", () => {
    let component: ActionableVariantViewModalComponent;
    let fixture: ComponentFixture<ActionableVariantViewModalComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ActionableVariantViewModalComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ActionableVariantViewModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
