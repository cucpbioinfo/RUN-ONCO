import { Component, OnInit } from "@angular/core";
import { AppStateService } from "../../../../services/app-state.service";
import { MessageCode } from "../../../../services/message-code";
import { FormBuilder, FormControl } from "@angular/forms";
import { Constants } from "../../../../services/constants";
import { UtilService } from "../../../../services/util.service";
import { ValidationService } from "../../../../services/validation.service";
import { Subject } from "rxjs";
import { BsModalRef } from "ngx-bootstrap/modal";
import { AlertDialogComponent } from "../../../../components/modal-dialog/alert-dialog/alert-dialog.component";
import { ModalService } from "../../../../services/modal.service";
import { ConfirmDialogComponent } from "../../../../components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { DataService } from "../../../../services/data.service";
import { UploadService } from '../../../../services/upload.service';
import { TranslateService } from "@ngx-translate/core";

@Component({
    selector: "app-rna-seq-form-modal",
    templateUrl: "./rna-seq-form-modal.component.html",
    styleUrls: ["./rna-seq-form-modal.component.scss"]
})
export class RnaSeqFormModalComponent implements OnInit {

    public rnaSeqForm: any;
    public submitted = false;
    public onClose: Subject<any>;
    private modalConfig: any;
    public errorMessage: string;

    public file: any;
    public fileToUpload: any;
    private validFileExtensions = [".exp"];

    public ddl: any = {};

    constructor(
        private msg: MessageCode,
        private fb: FormBuilder,
        public constants: Constants,
        private util: UtilService,
        public bsModalRef: BsModalRef,
        private dataService: DataService,
        private appState: AppStateService,
        private modalService: ModalService,
        private uploadService: UploadService,
        private translate: TranslateService
    ) {
        this.onClose = new Subject();
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        console.log("I:--START--:--OnLoad RnaSeqFormModalComponent--");
        this.submitted = false;
        this.buildForm();

        this.dataService
            .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_SAMPLE_SOURCE_LIST, this.appState.patient)
            .then((res: any) => {
                console.log('--->  res : ', res);
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl['sampleSourceList'] = res.data.items;
                }
            });
    }

    private buildForm(): void {
        this.rnaSeqForm = this.fb.group({
            id: [""],
            sampleSource: ["", [ValidationService.requiredValidator]],
            importFile: new FormControl(null)
        });
    }

    public trackByFn(index, item) {
        return index;
    }

    public onSave(): void {
        this.submitted = true;
        this.errorMessage = undefined;

        if (this.rnaSeqForm.valid && this.validateFile()) {
            const ctx = this.constants.CTX.SECURITY;
            const serviceName = this.constants.SERVICE_NAME.UPLOAD_SAMPLE_RNA_SEQ;
            this.uploadService.uploadFile(ctx, serviceName, this.formData).subscribe((res: any) => {
                console.log('---> res : ', res);
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.onClose.next(res);
                    this.bsModalRef.hide();
                } else {
                    this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                    this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
                }
            });
        }
    }

    private get formData(): FormData {
        const input = new FormData();
        input.append('responseData', JSON.stringify(this.req));
        input.append('importFile', this.rnaSeqForm.value.importFile);
        // Object.keys(this.rnaSeqForm.controls).forEach(key => {
        //     console.log('---> key : ', key);
        //     input.append(key, this.rnaSeqForm.controls[key].value);
        // });

        return input;
    }

    public handleFileSelect(files: any): void {
        this.errorMessage = undefined;
        // const files = evt.target.files;
        this.file = files[0];
        const isFileUploadValid = this.validateFile();
        if (isFileUploadValid) {
            this.rnaSeqForm.get('importFile').setValue(this.file);
        }
    }

    private get req() {
        const sampleRnaSeq: any = {};
        sampleRnaSeq.patient = {};
        sampleRnaSeq.patient.id = this.appState.patient.id;
        sampleRnaSeq.requestedUserId = this.appState.userInfo.userId;
        sampleRnaSeq.biospecimen = { id: this.rnaSeqForm.value.sampleSource };

        if (!this.util.isNullOrEmpty(this.rnaSeqForm.value.id)) {
            sampleRnaSeq.id = this.rnaSeqForm.value.id;
        }

        console.log('---> sampleRnaSeq : ', sampleRnaSeq);
        return sampleRnaSeq;
    }

    public deleteAttachment() {
        this.errorMessage = undefined;
        // this.files.splice(index, 1);
        this.modalConfig = { title: 'Message', message: 'Confirm delete item?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            if (confirm) {
                this.file = undefined;
                this.rnaSeqForm.get('importFile').setValue(null);
            }
        });
    }

    private validateFile(): boolean {
        if (!this.file) {
            this.errorMessage = "File upload is required";
            return false;
        }

        const isFileUploadValid = this.util.validateSingleInput(this.file, this.validFileExtensions);
        if (!isFileUploadValid) {
            this.errorMessage = `${this.file.name} is invalid, allowed extension is: ${this.validFileExtensions.join(", ")}`;
            return false;
        }

        if (this.file.size > this.constants.MAX_FILE_UPLOAD_SIZE) {
            const maxFileSize = this.util.getReadableFileSizeString(this.constants.MAX_FILE_UPLOAD_SIZE);
            this.errorMessage = this.translate.instant('msg.exceedMaxFileSize', { fileSize: maxFileSize });
            return false;
        }

        return true;
    }
}
