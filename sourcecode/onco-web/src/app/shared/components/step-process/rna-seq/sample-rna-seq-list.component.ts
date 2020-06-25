import { Component, OnInit } from '@angular/core';
import { RnaSeqFormModalComponent } from "../../app-modal/rna-seq/rna-seq-form-modal/rna-seq-form-modal.component";
import { ConfirmDialogComponent } from "../../modal-dialog/confirm-dialog/confirm-dialog.component";
import { AppStateService } from "../../../services/app-state.service";
import { Constants } from "../../../services/constants";
import { DataService } from "../../../services/data.service";
import { MessageCode } from "../../../services/message-code";
import { AlertDialogComponent } from "../../modal-dialog/alert-dialog/alert-dialog.component";
import { ModalService } from "../../../services/modal.service";
import { Router } from '@angular/router';
import { PathoViewModalComponent } from "../../../components/app-modal/clinical-data/patho-view-modal/patho-view-modal.component";
import { DownloadService } from "../../../services/download.service";
import { UtilService } from "../../../services/util.service";

@Component({
    selector: 'app-sample-rna-seq-list',
    templateUrl: './sample-rna-seq-list.component.html',
    styleUrls: ['./rna-seq-step-process.scss']
})
export class SampleRnaSeqListComponent implements OnInit {

    private modalConfig: any;
    public patient: any;
    public sampleRnaSeqList: any;

    constructor(private modalService: ModalService,
        private appState: AppStateService,
        public constants: Constants,
        private dataService: DataService,
        private msg: MessageCode,
        public router: Router,
        public downloadService: DownloadService,
        public util: UtilService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    public addItem(): void {
        const modalRef = this.modalService.show(RnaSeqFormModalComponent, 'modal-lg');
        modalRef.subscribe((sampleRnaSeq: any) => {
            console.log('--> sampleRnaSeq : ', sampleRnaSeq);
            this.doSearch();
        });
    }

    private init(): void {
        this.patient = this.appState.patient;
        console.log('I:--START--:--RnaSeq OnLoad--:patient/', this.patient);
        this.doSearch();
    }

    private doSearch(): void {
        this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_SAMPLE_RNA_SEQ_LIST, this.appState.patient)
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.sampleRnaSeqList = res.data;
                    console.log('---> this.sampleRnaSeqList : ', this.sampleRnaSeqList);
                } else if (res.responseStatus.responseCode !== this.msg.ERROR_DATA_NOT_FOUND.code) {
                    this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                    this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
                }
            });
    }

    public trackByFn(index, item) {
        return index;
    }

    public viewItem(item: any): void {
        if (item.status === this.constants.STATUS_ACTIVE) {
            this.appState.sampleRnaSeq = item;
            this.router.navigate(["view-rna-seq"]);
        }
    }

    public deleteItem(item: any): void {
        console.log('---> item : ', item);
        this.modalConfig = { title: 'Message', message: 'Confirm delete item?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            console.log('---> confirm : ', confirm);

            if (confirm) {
                this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.DELETE_RNA_SEQ, item)
                    .then((res: any) => {
                    if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                        this.modalConfig = { title: 'Message', message: 'Deleted successfully.' };
                        this.modalService.show(AlertDialogComponent, 'model-sm', this.modalConfig).subscribe(() => {
                            this.doSearch();
                        });
                    } else if (res.responseStatus.responseCode !== this.msg.ERROR_DATA_NOT_FOUND.code) {
                        this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                        this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
                    }
                });
            }
        });
    }

    public onDownload(item: any): void {
        const req: any = {};
        req.attachment = { attachmentId: item.attachmentId };

        const ctx = this.constants.CTX.SECURITY;
        const serviceName = this.constants.SERVICE_NAME.DOWNLOAD_FILE;
        this.downloadService.downloadFile(ctx, serviceName, req).subscribe((res: any) => {
            const disposition = res.headers.get('content-disposition');
            if (disposition && disposition.indexOf('attachment') !== -1) {
                const regex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                const matches = regex.exec(disposition);
                if (matches != null && matches[1]) {
                    const filename = matches[1].replace(/['"]/g, '');
                    this.util.saveFile(res._body, filename, 'application/octet-stream');
                }
            } else {
                const result = JSON.parse(res._body);
                this.modalConfig = { title: 'Message', message: result.responseStatus.responseMessage };
                this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
            }
        });
    }

    public viewPatho(item: any): void {
        this.modalConfig = { selected: item };
        console.log('I:--START--:--InitView Patho--:modalConfig/', this.modalConfig);
        const modalRef = this.modalService.show(PathoViewModalComponent, 'modal-lg', this.modalConfig);
        modalRef.subscribe((result: any) => {
            console.log('---> result : ', result);
        });
    }
}
