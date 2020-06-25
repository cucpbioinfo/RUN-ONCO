import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { PatientClustergrammerComponent } from "./patient-clustergrammer.component";

describe("PatientClustergrammerComponent", () => {
    let component: PatientClustergrammerComponent;
    let fixture: ComponentFixture<PatientClustergrammerComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [PatientClustergrammerComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(PatientClustergrammerComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
