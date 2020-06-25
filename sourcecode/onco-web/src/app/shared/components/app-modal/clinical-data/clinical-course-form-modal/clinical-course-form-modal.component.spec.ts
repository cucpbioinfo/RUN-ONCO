import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ClinicalCourseFormModalComponent } from "./clinical-course-form-modal.component";

describe("ClinicalCourseFormModalComponent", () => {
    let component: ClinicalCourseFormModalComponent;
    let fixture: ComponentFixture<ClinicalCourseFormModalComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ClinicalCourseFormModalComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ClinicalCourseFormModalComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
