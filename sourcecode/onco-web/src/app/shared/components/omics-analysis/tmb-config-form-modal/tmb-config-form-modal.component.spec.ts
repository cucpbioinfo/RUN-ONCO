import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { TmbConfigFormModalComponent } from "./tmb-config-form-modal.component";

describe("TmbConfigFormModalComponent", () => {
    let component: TmbConfigFormModalComponent;
    let fixture: ComponentFixture<TmbConfigFormModalComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [TmbConfigFormModalComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(TmbConfigFormModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
