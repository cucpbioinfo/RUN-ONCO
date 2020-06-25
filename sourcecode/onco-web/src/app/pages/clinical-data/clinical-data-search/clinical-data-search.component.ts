import { Component, OnInit } from "@angular/core";
import { AppStateService } from "../../../shared/services/app-state.service";
import { Router } from "@angular/router";
import { Constants } from "../../../shared/services/constants";
import { DataService } from "../../../shared/services/data.service";
import { MessageCode } from "../../../shared/services/message-code";
import { FormBuilder, FormGroup } from '@angular/forms';
import { DataTableResource } from "../../../shared/components/data-table";
import { ModalService } from "../../../shared/services/modal.service";
import { ConfirmDialogComponent } from "../../../shared/components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { AlertDialogComponent } from "../../../shared/components/modal-dialog/alert-dialog/alert-dialog.component";
import { UtilService } from "../../../shared/services/util.service";

@Component({
    selector: "app-clinical-data-search",
    templateUrl: "./clinical-data-search.component.html",
    styleUrls: ["./clinical-data-search.component.scss"]
})
export class ClinicalDataSearchComponent implements OnInit {

    public dataList: any;
    public searchForm: FormGroup;
    private modalConfig: any;
    private patient: any;
    public breadcrumbs: any;
    public isCollapsed = false;
    public message: string;

    itemResource = new DataTableResource([], 0);
    items = [];
    itemCount = 0;

    constructor(
        private appState: AppStateService,
        private router: Router,
        private constants: Constants,
        private dataService: DataService,
        private msg: MessageCode,
        private fb: FormBuilder,
        private modalService: ModalService,
        private util: UtilService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.buildForm();

        this.breadcrumbs = [
            {
                name: 'Search',
                url: '/clinical-data-search',
                active: true
            }
        ];
    }

    public reloadItems(params: any) {
        const obj: any = {};
        const criteria: any = {};
        criteria.status = this.constants.STATUS_ACTIVE;
        if (!this.util.isNullOrEmpty(this.searchForm.value.hn)) {
            const patient: any = {};
            patient.hn = this.searchForm.value.hn;
            criteria.patient = patient;
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
        // Reset parameter
        const dxIdx = this.dxIdx;

        this.appState.dxIdx = dxIdx;

        if (this.appState.clinicalData) {
            this.appState.clinicalData = { diagnosisList: [] };
            this.appState.currentDx = { pathoList: [], clinicalCourseList: [] };
        }

        if (this.patient) {
            this.appState.clinicalData.patient = this.patient;
        }

        this.router.navigate(["clinical-record-form/step1"]);
    }

    public search(): void {
        const obj: any = {};
        const criteria: any = {};
        criteria.status = this.constants.STATUS_ACTIVE;
        if (!this.util.isNullOrEmpty(this.searchForm.value.hn)) {
            const patient: any = {};
            patient.hn = this.searchForm.value.hn;
            criteria.patient = patient;
        }
        obj.criteria = criteria;
        obj.paging = { fetchSize: 15, startIndex: 0 };
        this.doSearch(obj);
    }

    private doSearch(criteria: any): void {
        this.dataService
            .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.SEARCH_CLINICAL_DATA, criteria)
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

    public trackByFn(index, item) {
        return index;
    }

    public editItem(data: any) {
        const objRequest = { id: data.id };
        this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_CLINICAL_DATA, objRequest)
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    const clinicalDto = res.data;

                    // Convert date
                    this.appState.clinicalData = this.prepareClinicalData(clinicalDto);

                    this.router.navigate(['clinical-record-form/step1']);
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
                const req = { ref: data.ref };
                this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.DELETE_CLINICAL_DATA, req).then((res: any) => {
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

    public onClear(): void {
        this.searchForm.reset();
        this.search();
    }

    public onSearch(): void {
        this.search();
    }

    private prepareClinicalData(clinicalDto: any): void {
        const cloned = JSON.parse(JSON.stringify(clinicalDto));

        cloned.diagnosisList.forEach((x: any) => {
            if (!x.pathoList) {
                x.pathoList = [];
            }
            if (!x.clinicalCourseList) {
                x.clinicalCourseList = [];
            }
        });

        return cloned;
    }

    private get dxIdx(): number {
        let idx = 0;
        const diagnosisList = this.appState.clinicalData.diagnosisList;

        if (diagnosisList && diagnosisList.length > 0) {
            idx = diagnosisList.length - 1;
        }
        return idx;
    }

    private buildForm(): void {
        this.searchForm = this.fb.group({
            hn: [""]
        });
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
