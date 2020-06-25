import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { PathoFormModalComponent } from "./patho-form-modal.component";

describe("PathoFormModalComponent", () => {
    let component: PathoFormModalComponent;
    let fixture: ComponentFixture<PathoFormModalComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [PathoFormModalComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(PathoFormModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
