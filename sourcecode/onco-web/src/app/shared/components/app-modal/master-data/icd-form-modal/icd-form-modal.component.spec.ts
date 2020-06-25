import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { IcdFormModalComponent } from "./icd-form-modal.component";

describe("IcdFormModalComponent", () => {
    let component: IcdFormModalComponent;
    let fixture: ComponentFixture<IcdFormModalComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [IcdFormModalComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(IcdFormModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
