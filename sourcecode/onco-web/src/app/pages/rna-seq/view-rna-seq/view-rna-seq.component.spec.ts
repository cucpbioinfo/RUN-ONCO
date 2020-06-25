import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ViewRnaSeqComponent } from "./view-rna-seq.component";

describe("ViewRnaSeqComponent", () => {
    let component: ViewRnaSeqComponent;
    let fixture: ComponentFixture<ViewRnaSeqComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ViewRnaSeqComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ViewRnaSeqComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
