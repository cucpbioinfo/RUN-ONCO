import { Component, OnInit, Input } from "@angular/core";
import { DataService } from "../../../services/data.service";
import { FormBuilder } from "@angular/forms";
import { Constants } from "../../../services/constants";
import { MessageCode } from "../../../services/message-code";
import { Router } from '@angular/router';
import { DataTableResource } from "../../data-table";
import { AlertDialogComponent } from "../../modal-dialog/alert-dialog/alert-dialog.component";
import { ModalService } from "../../../services/modal.service";
import { UtilService } from "../../../services/util.service";
import { ActionableVariantViewModalComponent } from "../../app-modal/variant-call/actionable-variant-view-modal/actionable-variant-view-modal.component";

@Component({
    selector: "app-variant-annotation-list",
    templateUrl: "./variant-annotation-list.component.html",
    styleUrls: ["./vc-step-process.scss"]
})
export class VariantAnnotationListComponent implements OnInit {

    public dataList: any;
    private modalConfig: any;
    private sampleVcf: any;

    itemResource = new DataTableResource([], 0);
    items = [];
    itemCount = 0;

    @Input() public data: any;

    constructor(public dataService: DataService,
        public fb: FormBuilder,
        public constants: Constants,
        public msg: MessageCode,
        public router: Router,
        private modalService: ModalService,
        private util: UtilService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.sampleVcf = this.data;
        this.search();
    }

    public search(): void {
        const obj: any = {};
        obj.criteria = this.sampleVcf;
        obj.paging = { fetchSize: 15, startIndex: 0 };
        this.doSearch(obj);
    }

    public reloadItems(params: any) {
        const obj: any = {};
        obj.criteria = this.sampleVcf;
        obj.paging = {
            fetchSize: params.limit,
            startIndex: params.offset,
            sortBy: params.sortBy,
            sortAsc: params.sortAsc
        };

        this.doSearch(obj);
    }

    private doSearch(criteria: any): void {
        this.dataService
            .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_VARIANT_CALL, criteria)
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {

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

    public onNavigate(url: string, item: string): void {
        this.util.openNewWindow(`${url}${item}`);
    }

    public showModalDialog(item: any): void {
        console.log('---> item: ', item);
        const req = { geneName: item.symbol };

        this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_ACTIONABLE_VARIATNT_BY_GENE, req)
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    // this.open(res.data);
                    this.modalConfig = { variantCall: item, annotatedVariantList: res.data };
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
}
