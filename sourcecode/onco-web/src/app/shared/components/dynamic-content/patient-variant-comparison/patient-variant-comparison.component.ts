import { Component, OnInit, Input, ViewChild } from "@angular/core";
import { AppStateService } from "../../../services/app-state.service";
import { VariantAnalysisFormComponent } from "../../../components/omics-analysis/variant-analysis-form/variant-analysis-form.component";
import { PreloadService } from "../../../services/preload.service";

@Component({
    selector: "app-patient-variant-comparison",
    templateUrl: "./patient-variant-comparison.component.html",
    styleUrls: ["./patient-variant-comparison.component.scss"]
})
export class PatientVariantComparisonComponent implements OnInit {

    public tab: any;
    public patient: any;
    public criteria: any;
    public variantParams: any;

    @Input() params: any;
    @ViewChild(VariantAnalysisFormComponent) variantAnalysisForm: VariantAnalysisFormComponent;

    constructor(
        private appState: AppStateService,
        private preload: PreloadService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.patient = this.params;
        this.appState.patient = this.patient;

        this.tab = {
            data: [
                { value: 1, name: "Variant Call" }
            ],
            step: 1,
            styleClass: "tablist"
        };
    }

    public search(criteria: any) {
        this.criteria = undefined;
        this.variantParams = undefined;

        this.preload.show();
        setTimeout(() => {
            this.criteria = criteria;
        }, 1000);
        this.preload.hide();
    }

    public updateVariantParams(criteria: any): void {
        this.variantParams = criteria;
        console.log("O:--SUCCESS--:--Update Params--:variantParams/", this.variantParams);
    }

    public get sampleVcfList() {
        if (this.variantAnalysisForm) {
            return this.variantAnalysisForm.sampleVcfList;
        }
    }

    public get initialize() {
        if (this.variantAnalysisForm) {
            return this.variantAnalysisForm.initialize;
        }

        return false;
    }
}
