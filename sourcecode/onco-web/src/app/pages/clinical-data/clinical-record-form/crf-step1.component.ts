import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { DataService } from "../../../shared/services/data.service";
import { Constants } from "../../../shared/services/constants";
import { AppStateService } from "../../../shared/services/app-state.service";
import { MessageCode } from "../../../shared/services/message-code";
import { ModalService } from "../../../shared/services/modal.service";
import { AlertDialogComponent } from "../../../shared/components/modal-dialog/alert-dialog/alert-dialog.component";
import { UtilService } from "../../../shared/services/util.service";

@Component({
    templateUrl: "./crf-step1.html",
    styleUrls: ["./crf.scss"]
})
export class CrfStep1Component implements OnInit {

    private modalConfig: any;
    public pageComponent: string;

    constructor(
        private dataService: DataService,
        private constants: Constants,
        private appState: AppStateService,
        private router: Router,
        private msg: MessageCode,
        private modalService: ModalService,
        private util: UtilService
    ) {
    }

    ngOnInit(): void {
        console.log("I:--START--:--OnLoad CRF Step1--");
        this.init();
    }

    private init(): void {
        const breadcrumbs = [
            {
                name: "Search",
                url: "/clinical-data-search",
                active: false
            },
            {
                name: "Diagnoses",
                url: "/clinical-record-form/step1",
                active: true
            }
        ];

        this.appState.updateObserver([
            { key: "BREADCRUMB", value: breadcrumbs }
        ]);

        this.pageComponent = 'clinical-data-search';

        // Scroll to top of page
        this.util.scrollTop();
    }

    public onCancel(): void {
        this.clearAppState();
        this.router.navigate(["clinical-data-search"]);
    }

    public onSave(): void {
        this.save().then((res: any) => {
            if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                this.router.navigate(['/clinical-data-search']);
            } else {
                this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
            }
        });
    }

    private save() {
        const p = new Promise(resolve => {
            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.SAVE_CLINICAL_DATA, this.req)
                .then((res: any) => {
                    resolve(res);
                });
        }).catch((ex: any) => {
            console.debug(ex);
        });

        return p;
    }

    private get req(): any {
        const data = this.appState.clinicalData;
        return data;
    }

    private clearAppState(): void {
        delete this.appState.dxIdx;
        delete this.appState.currentDx;
        delete this.appState.clinicalData;
    }

    public get patient() {
        return this.appState.clinicalData.patient;
    }

    public get isHidden(): boolean {
        return !this.patient;
    }

    public onHnChanged(obj: any): void {
        console.log('---> obj : ', obj);
        this.appState.clinicalData.patient = obj.patient;
    }
}
