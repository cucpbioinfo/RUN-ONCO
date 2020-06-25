import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ClustergrammerComponent } from "./clustergrammer.component";

describe("ClustergrammerComponent", () => {
    let component: ClustergrammerComponent;
    let fixture: ComponentFixture<ClustergrammerComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ClustergrammerComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ClustergrammerComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
