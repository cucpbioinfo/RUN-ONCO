import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { TmbScoreComponent } from "./tmb-score.component";

describe("TmbScoreComponent", () => {
    let component: TmbScoreComponent;
    let fixture: ComponentFixture<TmbScoreComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [TmbScoreComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(TmbScoreComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
