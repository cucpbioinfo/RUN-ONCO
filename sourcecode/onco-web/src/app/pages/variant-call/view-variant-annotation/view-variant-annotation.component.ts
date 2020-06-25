import { Component, OnInit } from "@angular/core";
import { AppStateService } from "../../../shared/services/app-state.service";

@Component({
    selector: "app-view-variant-annotation",
    templateUrl: "./view-variant-annotation.component.html",
    styleUrls: ["./view-variant-annotation.component.scss"]
})
export class ViewVariantAnnotationComponent implements OnInit {

    public patient: any;
    public biospecimen: any;
    public variantCall: any;

    constructor(
        public appState: AppStateService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        if (this.appState.patient) {
            this.patient = this.appState.patient;
        }
        if (this.appState.variantCall) {
            this.variantCall = this.appState.variantCall;
            this.biospecimen = this.appState.variantCall.biospecimen;
        }
    }

}
