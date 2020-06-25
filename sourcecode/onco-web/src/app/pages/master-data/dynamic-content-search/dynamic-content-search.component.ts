import { Component, OnInit } from "@angular/core";
import { AppStateService } from "../../../shared/services/app-state.service";
import { Constants } from "../../../shared/services/constants";
import { DataService } from "../../../shared/services/data.service";
import { MessageCode } from "../../../shared/services/message-code";
import { FormBuilder, FormGroup } from '@angular/forms';
import { DataTableResource } from "../../../shared/components/data-table";
import { ModalService } from "../../../shared/services/modal.service";
import { ConfirmDialogComponent } from "../../../shared/components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { AlertDialogComponent } from "../../../shared/components/modal-dialog/alert-dialog/alert-dialog.component";
import { DynamicContentModalComponent } from '../../../shared/components/app-modal/master-data/dynamic-content-modal/dynamic-content-modal.component';

@Component({
    selector: "app-dynamic-content-search",
    templateUrl: "./dynamic-content-search.component.html",
    styleUrls: ["./dynamic-content-search.component.scss"]
})
export class DynamicContentSearchComponent implements OnInit {

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
        public constants: Constants,
        private dataService: DataService,
        private msg: MessageCode,
        private fb: FormBuilder,
        private modalService: ModalService,
        private appState: AppStateService
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
                url: '/master-data/dynamic-content-search',
                active: true
            }
        ];
    }

    public reloadItems(params: any) {
        const obj: any = {};
        const criteria: any = {};
        criteria.analysisName = this.searchForm.value.analysisName;
        criteria.componentName = this.searchForm.value.componentName;
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
        criteria.analysisName = this.searchForm.value.analysisName;
        criteria.componentName = this.searchForm.value.componentName;
        obj.criteria = criteria;
        obj.paging = { fetchSize: 15, startIndex: 0 };
        this.doSearch(obj);
    }

    private doSearch(criteria: any): void {
        this.dataService
            .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.SEARCH_DYNAMIC_CONTENT, criteria)
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
            analysisName: [""],
            componentName: [""]
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

    public addItem(): void {
        console.log('I:--START--:--Add Item--');
        const modalRef = this.modalService.show(DynamicContentModalComponent, 'modal-lg');
        modalRef.subscribe((dynContent: any) => {
            console.log('--> dynContent : ', dynContent);
            this.search();
            this.getDynamicComponents();
        });
    }

    public editItem(item: any): void {
        console.log('---> item: ', item);
        this.modalConfig = { selected: item };
        console.log('I:--START--:--InitEdit DynamicContent--:modalConfig/', this.modalConfig);
        const modalRef = this.modalService.show(DynamicContentModalComponent, 'modal-lg', this.modalConfig);
        modalRef.subscribe((component: any) => {
            console.log('--> component : ', component);
            this.search();
            this.getDynamicComponents();
        });
    }

    public deleteItem(item: any): void {
        this.modalConfig = { title: 'Message', message: 'Confirm delete item?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            if (confirm) {
                const req: any = {};
                req.id = item.id;
                req.requestedUserId = this.appState.userInfo.userId;
                this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.DELETE_DYNAMIC_CONTENT, req).then((res: any) => {
                    if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                        this.modalConfig = { title: 'Message', message: 'Deleted successfully.' };
                        this.modalService.show(AlertDialogComponent, 'model-sm', this.modalConfig).subscribe(() => {
                            this.search();
                            this.getDynamicComponents();
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

    private getDynamicComponents() {
        this.dataService.connect('', this.constants.SERVICE_NAME.GET_DYNAMIC_CONTENT_LIST, {})
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.appState.dynamicComponents = res.data.items;
                } else if (res.responseStatus.responseCode === this.msg.ERROR_DATA_NOT_FOUND.code) {
                    this.appState.dynamicComponents = {};
                }
            });
    }
}
