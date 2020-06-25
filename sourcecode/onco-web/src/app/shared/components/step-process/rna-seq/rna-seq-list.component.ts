import { Component, OnInit, Input } from "@angular/core";
import { DataService } from "../../../services/data.service";
import { FormBuilder } from "@angular/forms";
import { Constants } from "../../../services/constants";
import { MessageCode } from "../../../services/message-code";
import { Router } from '@angular/router';
import { DataTableResource } from "../../data-table";
import { AlertDialogComponent } from "../../modal-dialog/alert-dialog/alert-dialog.component";
import { ModalService } from "../../../services/modal.service";

@Component({
    selector: "app-rna-seq-list",
    templateUrl: "./rna-seq-list.component.html",
    styleUrls: ["./rna-seq-step-process.scss"]
})
export class RnaSeqListComponent implements OnInit {

    public dataList: any;
    private modalConfig: any;
    private sampleRnaSeq: any;

    itemResource = new DataTableResource([], 0);
    items = [];
    itemCount = 0;

    @Input() public data: any;

    constructor(public dataService: DataService,
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

    private init(): void {
        this.sampleRnaSeq = this.data;
        this.search();
    }

    public search(): void {
        const obj: any = {};
        obj.criteria = this.sampleRnaSeq;
        obj.paging = { fetchSize: 15, startIndex: 0 };
        this.doSearch(obj);
    }

    reloadItems(params: any) {
        const obj: any = {};
        obj.criteria = this.sampleRnaSeq;
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
            .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_RNA_SEQ_LIST, criteria)
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
}
