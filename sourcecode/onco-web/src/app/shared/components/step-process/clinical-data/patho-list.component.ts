import { Component, OnInit } from '@angular/core';
import { ModalService } from "../../../services/modal.service";
import { PathoFormModalComponent } from "../../app-modal/clinical-data/patho-form-modal/patho-form-modal.component";
import { ConfirmDialogComponent } from "../../../components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { AppStateService } from "../../../services/app-state.service";
import { UtilService } from "../../../services/util.service";
import { Constants } from "../../../services/constants";

@Component({
    selector: 'app-patho-list',
    templateUrl: './patho-list.component.html',
    styleUrls: ['./crf-step-process.scss']
})
export class PathoListComponent implements OnInit {

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
        const modalRef = this.modalService.show(PathoFormModalComponent, 'modal-lg');
        modalRef.subscribe((patho: any) => {
            this.appState.currentDx.pathoList.push(patho);
        });
    }

    private init(): void {
        this.util.removeClass('.footer-actions', 'ext-footer-actions');
        if (!this.appState.currentDx.pathoList) {
            this.appState.currentDx.pathoList = [];
        }
    }

    public trackByFn(index, item) {
        return index;
    }

    public editItem(idx: number, item: any) {
        this.modalConfig = { selected: item };
        console.log('I:--START--:--InitEdit Patho--:modalConfig/', this.modalConfig);
        const modalRef = this.modalService.show(PathoFormModalComponent, 'modal-lg', this.modalConfig);
        modalRef.subscribe((patho: any) => {
            this.appState.currentDx.pathoList[idx] = patho;
        });
    }

    public deleteItem(idx: number) {
        this.modalConfig = { title: 'Message', message: 'Confirm delete item?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            console.log('---> confirm : ', confirm);

            if (confirm) {

                if (this.appState.currentDx.pathoList !== undefined
                    && this.appState.currentDx.pathoList[idx] !== undefined) {

                    const patho = this.appState.currentDx.pathoList[idx];

                    if (!this.util.isNullOrEmpty(patho.id)) {
                        patho.status = this.constants.STATUS_INACTIVE;
                    } else {
                        this.appState.currentDx.pathoList.splice(idx, 1);
                    }
                }
            }
        });
    }

    public get pathoList() {

        if (this.appState.currentDx) {
            const pathoList = this.appState.currentDx.pathoList;

            if (pathoList) {
                return pathoList.filter((x: any) => x.status === this.constants.STATUS_ACTIVE);
            }
        }

        return [];
    }
}
