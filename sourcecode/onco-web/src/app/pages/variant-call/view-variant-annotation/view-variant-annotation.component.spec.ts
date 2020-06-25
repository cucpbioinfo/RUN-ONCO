import { async, ComponentFixture, TestBed } from "@angular/core/testing";

import { ViewVariantAnnotationComponent } from "./view-variant-annotation.component";

describe("ViewVariantAnnotationComponent", () => {
    let component: ViewVariantAnnotationComponent;
    let fixture: ComponentFixture<ViewVariantAnnotationComponent>;

    beforeEach(async(() => {
        TestBed.configureTestingModule({
            declarations: [ViewVariantAnnotationComponent]
        }).compileComponents();
    }));

    beforeEach(() => {
        fixture = TestBed.createComponent(ViewVariantAnnotationComponent);
        component = fixture.componentInstance;
        fixture.detectChanges();
    });

    it("should create", () => {
        expect(component).toBeTruthy();
    });
});
