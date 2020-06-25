import { Component, OnInit, ChangeDetectorRef } from "@angular/core";
import { AppStateService } from "../../../shared/services/app-state.service";

@Component({
    templateUrl: "./crf.html",
    styleUrls: ["./crf.scss"]
})
export class CrfComponent implements OnInit {

    public observer: any;
    public subscription: any;
    public breadcrumbs: any = [];

    constructor(
        private appState: AppStateService,
        private cdRef: ChangeDetectorRef
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        // Initialize observer
        this.observer = this.appState.getObserver();
        this.subscription = this.observer.subscribe(result => {
            this.handleObserver(result);
        });
    }

    private handleObserver(dataList: Array<any>) {
        dataList.forEach(data => {
            if (data.key === 'BREADCRUMB') {
                this.breadcrumbs = data.value;
                console.log('---> this.breadcrumbs : ', this.breadcrumbs);
            }
        });

        this.cdRef.detectChanges();
    }
}
