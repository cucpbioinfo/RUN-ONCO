import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { RnaSeqFormModalComponent } from "./rna-seq-form-modal.component";

describe("RnaSeqFormModalComponent", () => {
    let component: RnaSeqFormModalComponent;
    let fixture: ComponentFixture<RnaSeqFormModalComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [RnaSeqFormModalComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(RnaSeqFormModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
