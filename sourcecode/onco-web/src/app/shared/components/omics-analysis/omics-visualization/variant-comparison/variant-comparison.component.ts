import { Component, OnInit, Input, OnChanges } from "@angular/core";
import { DataService } from "../../../../services/data.service";
import { FormBuilder } from "@angular/forms";
import { Constants } from "../../../../services/constants";
import { MessageCode } from "../../../../services/message-code";
import { Router } from "@angular/router";
import { DataTableResource } from "../../../data-table";
import { AlertDialogComponent } from "../../../modal-dialog/alert-dialog/alert-dialog.component";
import { ModalService } from "../../../../services/modal.service";
import { ActionableVariantViewModalComponent } from "../../../app-modal/variant-call/actionable-variant-view-modal/actionable-variant-view-modal.component";

@Component({
    selector: "app-variant-comparison",
    templateUrl: "./variant-comparison.component.html",
    styleUrls: ["./variant-comparison.component.scss"]
})
export class VariantComparisonComponent implements OnInit, OnChanges {

    public dataList: any;
    private modalConfig: any;
    // private initialized = false;
    public submitted: boolean;

    itemResource = new DataTableResource([], 0);
    items = [];
    itemCount = 0;
    columns = [];

    @Input() params: any;
    // @Input() sampleList: any;

    constructor(
        public dataService: DataService,
        public fb: FormBuilder,
        public constants: Constants,
        public msg: MessageCode,
        public router: Router,
        private modalService: ModalService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    ngOnChanges(): void {
        this.search();
    }

    private init(): void {
    }

    public search(): void {
        const obj: any = {};
        obj.criteria = this.params;
        obj.paging = { fetchSize: 15, startIndex: 0 };
        this.doSearch(obj);
    }

    public reloadItems(params: any) {
        // if (this.initialized) {
            const obj: any = {};
            obj.criteria = this.params;
            obj.paging = {
                fetchSize: params.limit,
                startIndex: params.offset,
                sortBy: params.sortBy,
                sortAsc: params.sortAsc
            };

            this.doSearch(obj);
        // }
    }

    private doSearch(criteria: any): void {
        this.dataService
            .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_VARIANT_COMPARISON, criteria)
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {

                    if (res.data) {
                        this.dataList = res.data.elements;
                        this.itemCount = res.data.totalElements;
                        this.columns = res.data.columns;
                    } else {
                        this.dataList = [];
                        this.itemCount = 0;
                        this.columns = [];
                    }

                    this.itemResource = new DataTableResource(this.dataList, this.itemCount);
                    this.itemResource.query({}).then(items => (this.items = items));
                } else if (res.responseStatus.responseCode !== this.msg.ERROR_DATA_NOT_FOUND.code) {
                    this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                    this.modalService.show(AlertDialogComponent, 'model-sm', this.modalConfig);
                }

                // this.initialized = true;
            });
    }

    public showModalDialog(item: any): void {
        console.log('---> item: ', item);
        const idx = this.columns.findIndex(x => x.sortBy === "symbol");
        const req = { geneName: item[idx] };
        console.log('---> req : ', req);

        this.dataService
            .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_ACTIONABLE_VARIATNT_BY_GENE, req)
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.modalConfig = { annotatedVariantList: res.data };
                    const modalRef = this.modalService.show(ActionableVariantViewModalComponent, 'modal-lg', this.modalConfig);
                    modalRef.subscribe((xxx: any) => {
                        console.log('---> xxx : ', xxx);
                    });
                } else if (res.responseStatus.responseCode !== this.msg.ERROR_DATA_NOT_FOUND.code) {
                    this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                    this.modalService.show(AlertDialogComponent, 'model-sm', this.modalConfig);
                }
            });

    }

    public trackByFn(index, item) {
        return index;
    }

    public get startCol() {
        if (this.columns && this.params.sampleVcfIds) {
            return this.columns.length - this.params.sampleVcfIds.length;
        }

        return 0;
    }
}
