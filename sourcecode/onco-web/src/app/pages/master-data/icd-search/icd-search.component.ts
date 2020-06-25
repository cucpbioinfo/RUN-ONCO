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
import { IcdFormModalComponent } from '../../../shared/components/app-modal/master-data/icd-form-modal/icd-form-modal.component';
import { DownloadService } from '../../../shared/services/download.service';
import { UtilService } from '../../../shared/services/util.service';

@Component({
    selector: "app-icd-search",
    templateUrl: "./icd-search.component.html",
    styleUrls: ["./icd-search.component.scss"]
})
export class IcdSearchComponent implements OnInit {

    public dataList: any;
    public searchForm: FormGroup;
    private modalConfig: any;
    public breadcrumbs: any;
    public isCollapsed = false;
    public message: string;

    itemResource = new DataTableResource([], 0);
    items = [];
    itemCount = 0;

    constructor(
        private appState: AppStateService,
        private router: Router,
        public constants: Constants,
        private dataService: DataService,
        private msg: MessageCode,
        private fb: FormBuilder,
        private modalService: ModalService,
        private downloadService: DownloadService,
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
                name: 'List',
                url: '/master-data/list',
                active: false
            },
            {
                name: 'Search',
                url: '/master-data/icd-search',
                active: true
            }
        ];
    }

    public reloadItems(params: any) {
        const obj: any = {};
        const criteria: any = {};
        criteria.type = 'ICD';
        criteria.version = this.searchForm.value.dataVersion;
        obj.criteria = criteria;
        obj.paging = {
            fetchSize: params.limit,
            startIndex: params.offset,
            sortBy: params.sortBy,
            sortAsc: params.sortAsc
        };

        this.doSearch(obj);
    }

    private search(): void {
        const obj: any = {};
        const criteria: any = {};
        criteria.type = 'ICD';
        criteria.version = this.searchForm.value.dataVersion;
        obj.criteria = criteria;
        obj.paging = { fetchSize: 15, startIndex: 0 };
        this.doSearch(obj);
    }

    private doSearch(criteria: any): void {
        this.dataService
            .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.SEARCH_DATA_VERSION, criteria)
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

    private buildForm(): void {
        this.searchForm = this.fb.group({
            dataVersion: [""]
        });
    }

    public trackByFn(index, item) {
        return index;
    }

    public onClear(): void {
        this.searchForm.reset();
        this.search();
    }

    public onSearch(): void {
        this.search();
    }

    public onDownload(): void {
        console.log('I:--START--:--Download ICD Template--');
        const ctx = this.constants.CTX.SECURITY;
        const serviceName = this.constants.SERVICE_NAME.DOWNLOAD_ICD_TEMPLATE;
        const req: any = {};
        req.type = 'ICD';
        req.fileName = 'example-template(ICD).csv';

        this.downloadService.downloadFile(ctx, serviceName, req).subscribe((res: any) => {
            // console.log('---> res : ', res);
            const disposition = res.headers.get('content-disposition');
            if (disposition && disposition.indexOf('attachment') !== -1) {
                const regex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                const matches = regex.exec(disposition);
                if (matches != null && matches[1]) {
                    const filename = matches[1].replace(/['"]/g, '');
                    const content = res._body;
                    this.util.saveFile(content, filename, 'application/octet-stream');
                }
            }

            console.log('O:--SUCCESS--:--Download ICD Template--');
        });
    }

    public import(): void {
        console.log('I:--START--:--Import Data--');
        const modalRef = this.modalService.show(IcdFormModalComponent, 'modal-lg');
        modalRef.subscribe((icd: any) => {
            console.log('--> icd : ', icd);
            this.search();
        });
    }

    public editItem(item: any): void {
        console.log('---> item: ', item);
        this.modalConfig = { selected: item };
        console.log('I:--START--:--InitEdit ICD--:modalConfig/', this.modalConfig);
        const modalRef = this.modalService.show(IcdFormModalComponent, 'modal-lg', this.modalConfig);
        modalRef.subscribe((icd: any) => {
            console.log('--> icd : ', icd);
            this.search();
        });
    }

    public viewItem(item: any): void {
        console.log('---> item: ', item);
        this.appState.formData = item;
        this.router.navigate(['/master-data/view-icd']);
    }

    public deleteItem(item: any): void {
        this.modalConfig = { title: 'Message', message: 'Confirm delete item?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            if (confirm) {
                const req: any = {};
                req.id = item.id;
                req.type = 'ICD';
                this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.DELETE_DATA_VERSION, req).then((res: any) => {
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
