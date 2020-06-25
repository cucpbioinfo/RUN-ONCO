import { Component, OnInit, AfterViewInit } from '@angular/core';
import { AppStateService } from '../../../services/app-state.service';
import { ModalService } from "../../../services/modal.service";
import { ClinicalCourseFormModalComponent } from "../../app-modal/clinical-data/clinical-course-form-modal/clinical-course-form-modal.component";
import { ConfirmDialogComponent } from "../../../../shared/components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { UtilService } from "../../../services/util.service";
import { Constants } from "../../../services/constants";

@Component({
    selector: 'app-clinical-course-list',
    templateUrl: './clinical-course-list.component.html',
    styleUrls: ['./crf-step-process.scss']
})
export class ClinicalCourseListComponent implements OnInit, AfterViewInit {

    private modalConfig: any;

    constructor(private appState: AppStateService,
        private modalService: ModalService,
        private util: UtilService,
        private constants: Constants) {

    }

    ngOnInit(): void {
        this.init();
    }

    ngAfterViewInit(): void {

    }

    public addItem(): void {
        const modalRef = this.modalService.show(ClinicalCourseFormModalComponent, 'modal-lg');
        modalRef.subscribe((clinicalCourse: any) => {
            this.appState.currentDx.clinicalCourseList.push(clinicalCourse);
        });
    }

    public editItem(idx: number, item: any): void {
        this.modalConfig = { selected: item };
        console.log('I:--START--:--InitEdit ClinicalCourse--:modalConfig/', this.modalConfig);
        const modalRef = this.modalService.show(ClinicalCourseFormModalComponent, 'modal-lg', this.modalConfig);
        modalRef.subscribe((clinicalCourse: any) => {
            this.appState.currentDx.clinicalCourseList[idx] = clinicalCourse;
        });
    }

    public deleteItem(idx: number): void {

        this.modalConfig = { title: 'Message', message: 'Confirm delete item?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            console.log('---> confirm : ', confirm);

            if (confirm) {

                if (this.appState.currentDx.clinicalCourseList !== undefined
                    && this.appState.currentDx.clinicalCourseList[idx] !== undefined) {

                    const clinicalCourse = this.appState.currentDx.clinicalCourseList[idx];

                    if (!this.util.isNullOrEmpty(clinicalCourse.id)) {
                        clinicalCourse.status = this.constants.STATUS_INACTIVE;
                    } else {
                        this.appState.currentDx.clinicalCourseList.splice(idx, 1);
                    }
                }
            }
        });
    }

    private init(): void {
        this.util.removeClass('.footer-actions', 'ext-footer-actions');
    }

    public trackByFn(index, item) {
        return index;
    }

    public get clinicalCourseList() {

        if (this.appState.currentDx) {
            const clinicalCourseList = this.appState.currentDx.clinicalCourseList;

            if (clinicalCourseList) {
                return clinicalCourseList.filter((x: any) => x.status === this.constants.STATUS_ACTIVE);
            }
        }

        return [];
    }
}
