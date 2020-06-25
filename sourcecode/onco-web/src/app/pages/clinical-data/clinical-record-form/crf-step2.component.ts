import { Component, OnInit } from '@angular/core';
import { AppStateService } from "../../../shared/services/app-state.service";

@Component({
    templateUrl: './crf-step2.html',
    styleUrls: ['./crf.scss']
})
export class CrfStep2Component implements OnInit {

    constructor(
        private appState: AppStateService
    ) {
    }

    ngOnInit(): void {
        console.log('I:--START--:--OnLoad CRF Step2--');
        this.init();
    }

    init(): void {
        const breadcrumbs = [
            {
                name: "Search",
                url: "/clinical-data-search",
                active: false
            },
            {
                name: "Diagnoses",
                url: "/clinical-record-form/step1",
                active: false
            },
            {
                name: "Diagnosis Form",
                url: "/clinical-record-form/step2",
                active: true
            }
        ];

        this.appState.updateObserver([
            { key: "BREADCRUMB", value: breadcrumbs }
        ]);
    }
}
