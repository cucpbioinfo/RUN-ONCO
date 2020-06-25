import { Component, OnInit } from "@angular/core";
import { UtilService } from "../../../shared/services/util.service";
import { AppStateService } from "../../../shared/services/app-state.service";
import { AlertDialogComponent } from "../../../shared/components/modal-dialog/alert-dialog/alert-dialog.component";
import { Constants } from "../../../shared/services/constants";
import { DataService } from "../../../shared/services/data.service";
import { MessageCode } from "../../../shared/services/message-code";
import { DataTableResource } from "../../../shared/components/data-table";
import { ModalService } from "../../../shared/services/modal.service";

@Component({
    selector: "app-view-icd-o",
    templateUrl: "./view-icd-o.component.html",
    styleUrls: ["./view-icd-o.component.scss"]
})
export class ViewIcdOComponent implements OnInit {

    public dataList: any;
    public submitted: boolean;
    public breadcrumbs: any;
    private modalConfig: any;
    public dataVersion: any;

    itemResource = new DataTableResource([], 0);
    items = [];
    itemCount = 0;

    constructor(
        public constants: Constants,
        public appState: AppStateService,
        public dataService: DataService,
        public msg: MessageCode,
        public util: UtilService,
        private modalService: ModalService,
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.dataVersion = this.appState.formData;

        this.breadcrumbs = [
            {
                name: 'List',
                url: '/master-data/list',
                active: false
            },
            {
                name: 'Search',
                url: '/master-data/icd-o-search',
                active: false
            },
            {
                name: 'View ICD-O',
                url: '/master-data/view-icd-o',
                active: true
            }
        ];
    }

    public reloadItems(params: any) {
        const obj: any = {};
        const criteria: any = {};
        criteria.dataVerId = this.dataVersion.id;
        obj.criteria = criteria;
        obj.paging = {
            fetchSize: params.limit,
            startIndex: params.offset,
            sortBy: params.sortBy,
            sortAsc: params.sortAsc
        };

        this.doSearch(obj);
    }

    public search(): void {
        const obj: any = {};
        const criteria: any = {};
        criteria.dataVerId = this.dataVersion.id;
        obj.criteria = criteria;
        obj.paging = { fetchSize: 15, startIndex: 0 };
        this.doSearch(obj);
    }

    private doSearch(criteria: any): void {
        this.dataService
            .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.SEARCH_ICD_O, criteria)
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
}
