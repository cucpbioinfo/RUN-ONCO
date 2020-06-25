import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { Constants } from "../../../shared/services/constants";
import { DataService } from "../../../shared/services/data.service";
import { MessageCode } from "../../../shared/services/message-code";
import { Router } from '@angular/router';
import { DataTableResource } from "../../../shared/components/data-table";
import { AlertDialogComponent } from "../../../shared/components/modal-dialog/alert-dialog/alert-dialog.component";
import { ModalService } from "../../../shared/services/modal.service";
import { AppStateService } from "../../../shared/services/app-state.service";
import { ConfirmDialogComponent } from "../../../shared/components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { ValidationService } from "../../../shared/services/validation.service";
import { UtilService } from "../../../shared/services/util.service";

@Component({
    selector: "app-patient-search",
    templateUrl: "./patient-search.component.html",
    styleUrls: ["./patient-search.component.scss"]
})
export class PatientSearchComponent implements OnInit {

    public dataList: any;
    public searchForm: FormGroup;
    private modalConfig: any;
    public breadcrumbs: any;
    public isCollapsed = false;
    public message: string;

    itemResource = new DataTableResource([], 0);
    items = [];
    itemCount = 0;

    constructor(public fb: FormBuilder,
        public constants: Constants,
        public dataService: DataService,
        public msg: MessageCode,
        public router: Router,
        private modalService: ModalService,
        private appState: AppStateService,
        private util: UtilService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.breadcrumbs = [
            {
                name: 'Search',
                url: '/patient-search',
                active: true
            }
        ];

        this.buildForm();
    }

    private buildForm(): void {
        this.searchForm = this.fb.group({
            hn: ["", [ValidationService.requiredValidator]],
        });
    }

    private search(): void {
        const obj: any = {};
        const criteria: any = {};
        criteria.status = this.constants.STATUS_ACTIVE;
        if (!this.util.isNullOrEmpty(this.searchForm.value.hn)) {
            criteria.hn = this.searchForm.value.hn;
        }
        obj.criteria = criteria;
        obj.paging = { fetchSize: 15, startIndex: 0 };
        this.doSearch(obj);
    }

    public reloadItems(params: any) {
        const obj: any = {};
        const criteria: any = {};
        criteria.status = this.constants.STATUS_ACTIVE;
        if (!this.util.isNullOrEmpty(this.searchForm.value.hn)) {
            criteria.hn = this.searchForm.value.hn;
        }
        obj.criteria = criteria;
        obj.paging = {
            fetchSize: params.limit,
            startIndex: params.offset,
            sortBy: params.sortBy,
            sortAsc: params.sortAsc
        };
        this.doSearch(obj);
    }

    public addNew(): void {
        this.appState.patient = {};
        this.appState.patient.status = this.constants.STATUS_ACTIVE;
        this.appState.patient.survivalFollowupList = [];
        this.router.navigate(["patient-form"]);
    }

    private doSearch(criteria: any): void {
        this.dataService
            .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.SEARCH_PATIENT, criteria)
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code
                    || res.responseStatus.responseCode === this.msg.ERROR_DATA_NOT_FOUND.code) {

                    if (res.data) {
                        this.dataList = res.data.elements;
                        this.itemCount = res.data.totalElements;
                    } else {
                        this.dataList = [];
                        this.itemCount = 0;
                    }

                    this.itemResource = new DataTableResource(this.dataList, this.itemCount);
                    this.itemResource.query({}).then(items => (this.items = items));
                } else if (res.responseStatus.responseCode !== this.msg.ERROR_DATA_NOT_FOUND.code) {
                    this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                    this.modalService.show(AlertDialogComponent, 'model-sm', this.modalConfig);
                }
            });
    }

    public editItem(data: any) {
        const req = { id: data.id };
        this.getPatient(req).then((res: any) => {
            if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                const patientDto = res.data;
                this.appState.patient = this.preparePatient(patientDto);
                this.router.navigate(['patient-form']);
            } else {
                this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
            }
        });
    }

    public deleteItem(data: any) {
        this.modalConfig = { title: 'Message', message: 'Confirm delete item?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            if (confirm) {
                const req = { id: data.id };
                this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.DELETE_PATIENT, req).then((res: any) => {
                    if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                        this.modalConfig = { title: 'Message', message: 'Deleted successfully.' };
                        this.modalService.show(AlertDialogComponent, 'model-sm', this.modalConfig).subscribe(() => {
                            this.search();
                        });
                    } else {
                        this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                        this.modalService.show(AlertDialogComponent, 'model-sm', this.modalConfig);
                    }
                });
            }
        });
    }

    public viewAnalysis(data: any) {const req = { id: data.id };
        this.getPatient(req).then((res: any) => {
            if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                const patientDto = res.data;
                this.appState.patient = this.preparePatient(patientDto);
                this.router.navigate(['patient-analysis']);
            } else {
                this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
            }
        });
    }

    public onClear(): void {
        this.searchForm.reset();
        this.search();
    }

    public onSearch(): void {
        this.search();
    }

    private preparePatient(patientDto: any): void {
        const cloned = JSON.parse(JSON.stringify(patientDto));

        if (!cloned.survivalFollowupList) {
            cloned.survivalFollowupList = [];
        }

        return cloned;
    }

    private getPatient(req: any) {
        const p = new Promise(resolve => {
            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_PATIENT, req).then((res: any) => {
                resolve(res);
            });
        });

        return p;
    }

    public collapsed(): void {
        this.message = 'collapsed';
    }

    public collapses(): void {
        this.message = 'collapses';
    }

    public expanded(): void {
        this.message = 'expanded';
    }

    public expands(): void {
        this.message = 'expands';
    }
}
