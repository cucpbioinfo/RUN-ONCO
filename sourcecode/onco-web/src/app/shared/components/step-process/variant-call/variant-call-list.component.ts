import { Component, OnInit } from '@angular/core';
import { VariantCallFormModalComponent } from "../../app-modal/variant-call/variant-call-form-modal/variant-call-form-modal.component";
import { ConfirmDialogComponent } from "../../../components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { AppStateService } from "../../../services/app-state.service";
import { Constants } from "../../../services/constants";
import { DataService } from "../../../services/data.service";
import { MessageCode } from "../../../services/message-code";
import { AlertDialogComponent } from "../../../components/modal-dialog/alert-dialog/alert-dialog.component";
import { ModalService } from "../../../services/modal.service";
import { Router } from '@angular/router';
import { PathoViewModalComponent } from "../../../components/app-modal/clinical-data/patho-view-modal/patho-view-modal.component";
import { DownloadService } from "../../../services/download.service";
import { UtilService } from "../../../services/util.service";

@Component({
    selector: 'app-variant-call-list',
    templateUrl: './variant-call-list.component.html',
    styleUrls: ['./vc-step-process.scss']
})
export class VariantCallListComponent implements OnInit {

    private modalConfig: any;
    public patient: any;
    public sampleVcfList: any;

    constructor(
        private modalService: ModalService,
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
        const modalRef = this.modalService.show(VariantCallFormModalComponent, 'modal-lg');
        modalRef.subscribe((sampleVcf: any) => {
            console.log('--> sampleVcf : ', sampleVcf);
            this.doSearch();
        });
    }

    private init(): void {
        this.patient = this.appState.patient;
        console.log('I:--START--:--VariantCall OnLoad--:patient/', this.patient);
        this.doSearch();
    }

    private doSearch(): void {
        this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_SAMPLE_VCF_LIST_BY_PATIENT, this.appState.patient)
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.sampleVcfList = res.data;
                    console.log('---> this.sampleVcfList : ', this.sampleVcfList);
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
            this.appState.variantCall = item;
            this.router.navigate(["view-variant-annotation"]);
        }
    }

    public deleteItem(item: any): void {
        console.log('----> item :', item);
        this.modalConfig = { title: 'Message', message: 'Confirm delete item?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            console.log('---> confirm : ', confirm);

            if (confirm) {
                this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.DELETE_VARIANT_CALL, item)
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
            }  else {
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
