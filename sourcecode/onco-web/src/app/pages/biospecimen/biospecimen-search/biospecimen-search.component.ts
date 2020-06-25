import { Component, OnInit } from "@angular/core";
import { AppStateService } from "../../../shared/services/app-state.service";
import { Router } from "@angular/router";
import { Constants } from "../../../shared/services/constants";
import { MessageCode } from "../../../shared/services/message-code";
import { FormBuilder } from '@angular/forms';
import { DataTableResource } from "../../../shared/components/data-table";
import { ModalService } from "../../../shared/services/modal.service";
import { ConfirmDialogComponent } from "../../../shared/components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { AlertDialogComponent } from "../../../shared/components/modal-dialog/alert-dialog/alert-dialog.component";
import { UtilService } from "../../../shared/services/util.service";
import { DataService } from "../../../shared/services/data.service";

@Component({
    selector: "app-biospecimen-search",
    templateUrl: "./biospecimen-search.component.html",
    styleUrls: ["./biospecimen-search.component.scss"]
})
export class BiospecimenSearchComponent implements OnInit {

    public dataList: any;
    private modalConfig: any;
    public searchForm: any;
    private patient: any;
    public submitted = false;
    public breadcrumbs: any;
    public isCollapsed = false;
    public message: string;

    itemResource = new DataTableResource([], 0);
    items = [];
    itemCount = 0;

    constructor(private appState: AppStateService,
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
        this.search();

        this.breadcrumbs = [
            {
                name: 'Search',
                url: '/biospecimen-search',
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

    public addItem(): void {
        this.appState.biospecimen = {};
        this.appState.biospecimen.status = this.constants.STATUS_ACTIVE;

        if (this.patient) {
            this.appState.biospecimen.patient = this.patient;
        }

        this.router.navigate(["biospecimen-form"]);
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
        this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.SEARCH_BIOSPECIMEN, criteria)
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
        this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_BIOSPECIMEN, objRequest)
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    const biospecimenDto = res.data;
                    this.appState.biospecimen = biospecimenDto;
                    this.router.navigate(['biospecimen-form']);
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
                this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.DELETE_BIOSPECIMEN, req).then((res: any) => {

                    if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                        console.log('---> res: ', res);
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

    private buildForm(): void {
        this.searchForm = this.fb.group({
            hn: [""]
        });
    }

    public onClear(): void {
        this.searchForm.reset();
        this.search();
    }

    public onSearch(): void {
        this.search();
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
