import { Component, OnInit, AfterViewInit } from '@angular/core';
import { AppStateService } from '../../../services/app-state.service';
import { ModalService } from "../../../services/modal.service";
import { Router } from "@angular/router";
import { ConfirmDialogComponent } from "../../../components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { Constants } from "../../../services/constants";
import { UtilService } from "../../../services/util.service";

@Component({
    selector: 'app-diagnosis-list',
    templateUrl: './diagnosis-list.component.html',
    styleUrls: ['./crf-step-process.scss']
})
export class DiagnosisListComponent implements OnInit, AfterViewInit {

    private modalConfig: any;

    constructor(private appState: AppStateService,
        private router: Router,
        private modalService: ModalService,
        private constants: Constants,
        private util: UtilService) {

    }

    ngOnInit(): void {
        this.init();
    }

    ngAfterViewInit(): void {
        console.log('---> this.appState.clinicalData.diagnosisList : ',  this.appState.clinicalData.diagnosisList);
    }

    public addItem(): void {
        let idx = 0;
        const diagnosisList = this.appState.clinicalData.diagnosisList;

        if (JSON.stringify(diagnosisList) !== '[]' && diagnosisList.length > 0) {
            idx = diagnosisList.length;
        }

        this.appState.dxIdx = idx;
        console.log('------> this.appState.dxIdx : ', this.appState.dxIdx);
        console.log('------> diagnosisList : ', diagnosisList);
        console.log('------> currentDx : ', diagnosisList[this.appState.dxIdx]);
        this.router.navigate(['clinical-record-form/step2']);
    }

    private init(): void {
    }

    public trackByFn(index, item) {
        return index;
    }

    public get diagnosisList() {
        const diagnosisList = this.appState.clinicalData.diagnosisList;

        if (diagnosisList) {
            return diagnosisList.filter((x: any) => x.status === this.constants.STATUS_ACTIVE);
        }

        return diagnosisList;
    }

    public get isDiagnosisEmpty(): boolean {

        if (this.diagnosisList) {
            const count = this.diagnosisList.filter((x: any) => x.diagnosisDate).length;
            return count === 0;
        }

        return true;
    }

    public editItem(idx: number, item: any) {
        this.appState.dxIdx = idx;
        console.log('---> this.appState.currentDx : ', this.appState.currentDx);
        this.router.navigate(['clinical-record-form/step2']);
    }

    public deleteItem(idx: number) {
        this.modalConfig = { title: 'Message', message: 'Confirm delete item?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            console.log('---> confirm : ', confirm);

            if (confirm) {

                if (this.appState.clinicalData.diagnosisList !== undefined
                    && this.appState.clinicalData.diagnosisList !== undefined) {

                    const diagnosis = this.appState.clinicalData.diagnosisList[idx];

                    if (!this.util.isNullOrEmpty(diagnosis.id)) {
                        diagnosis.status = this.constants.STATUS_INACTIVE;
                    } else {
                        this.appState.clinicalData.diagnosisList.splice(idx, 1);
                    }
                }
            }
        });
    }
}
