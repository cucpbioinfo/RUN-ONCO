import { Component, OnInit } from '@angular/core';
import { ModalService } from "../../../services/modal.service";
import { SurvivalFollowupFormModalComponent } from "../../app-modal/clinical-data/survival-followup-form-modal/survival-followup-form-modal.component";
import { ConfirmDialogComponent } from "../../../components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { AppStateService } from "../../../services/app-state.service";
import { UtilService } from "../../../services/util.service";
import { Constants } from "../../../services/constants";

@Component({
    selector: 'app-survival-followup-list',
    templateUrl: './survival-followup-list.component.html',
    styleUrls: ['./crf-step-process.scss']
})
export class SurvivalFollowupListComponent implements OnInit {

    private modalConfig: any;

    constructor(private modalService: ModalService,
        private appState: AppStateService,
        private util: UtilService,
        private constants: Constants
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    public addItem(): void {
        const modalRef = this.modalService.show(SurvivalFollowupFormModalComponent, 'modal-lg');
        modalRef.subscribe((survivalFollowup: any) => {
            console.log('--> survivalFollowup : ', survivalFollowup);
            this.appState.patient.survivalFollowupList.push(survivalFollowup);
        });
    }

    private init(): void {
        console.log('--> this.appState.patient : ', this.appState.patient);
    }

    public trackByFn(index, item) {
        return index;
    }

    public editItem(idx: number, item: any) {
        this.modalConfig = { selected: item };
        console.log('I:--START--:--InitEdit SurvivalFollowup--:modalConfig/', this.modalConfig);
        const modalRef = this.modalService.show(SurvivalFollowupFormModalComponent, 'modal-lg', this.modalConfig);
        modalRef.subscribe((survivalFollowup: any) => {
            console.log('--> idx : ', idx, ' | survivalFollowup : ', survivalFollowup);
            this.appState.patient.survivalFollowupList[idx] = survivalFollowup;
            console.log('--> survivalFollowupList : ', this.appState.patient.survivalFollowupList);
        });
    }

    public deleteItem(idx: number) {
        this.modalConfig = { title: 'Message', message: 'Confirm delete item?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            console.log('---> confirm : ', confirm);

            if (confirm) {

                if (this.appState.patient.survivalFollowupList !== undefined
                    && this.appState.patient.survivalFollowupList[idx] !== undefined) {

                    const survivalFollowup = this.appState.patient.survivalFollowupList[idx];

                    if (!this.util.isNullOrEmpty(survivalFollowup.id)) {
                        survivalFollowup.status = this.constants.STATUS_INACTIVE;
                    } else {
                        this.appState.patient.survivalFollowupList.splice(idx, 1);
                    }
                }
            }
        });
    }

    public get survivalFollowupList() {
        const survivalFollowupList = this.appState.patient.survivalFollowupList;

        if (!this.util.isEmptyObject(survivalFollowupList)) {
            return survivalFollowupList.filter((x: any) => x.status === this.constants.STATUS_ACTIVE);
        }

        return [];
    }
}
