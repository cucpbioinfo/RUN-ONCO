import { Component, OnInit } from "@angular/core";
import { AppStateService } from "../../../shared/services/app-state.service";

@Component({
    selector: "app-view-rna-seq",
    templateUrl: "./view-rna-seq.component.html",
    styleUrls: ["./view-rna-seq.component.scss"]
})
export class ViewRnaSeqComponent implements OnInit {

    public patient: any;
    public biospecimen: any;
    public sampleRnaSeq: any;
    public breadcrumbs: any;

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
            this.sampleRnaSeq = this.appState.sampleRnaSeq;
            this.biospecimen = this.appState.sampleRnaSeq.biospecimen;
        }

        this.breadcrumbs = this.appState.breadcrumbs || [];
        console.log('---> this.appState.breadcrumbs : ', this.appState.breadcrumbs);

        if (this.breadcrumbs.length > 0) {
            this.breadcrumbs.forEach((x: any) => {
                x.active = false;
            });
        }
        this.breadcrumbs.push({ name: "RNA-Seq", url: "/view-rna-seq", active: true });
    }
}
