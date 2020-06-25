import { Component, OnInit } from "@angular/core";
import { DataService } from "../../../../services/data.service";
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
import { UploadService } from '../../../../services/upload.service';
import { TranslateService } from "@ngx-translate/core";

@Component({
    selector: "app-variant-call-form-modal",
    templateUrl: "./variant-call-form-modal.component.html",
    styleUrls: ["./variant-call-form-modal.component.scss"]
})
export class VariantCallFormModalComponent implements OnInit {

    public variantCallForm: any;
    public submitted = false;
    public onClose: Subject<any>;
    private modalConfig: any;
    public errorMessage: string;

    public file: any;
    private validFileExtensions = [".vcf"];

    public ddl: any = {
        seqTypeList: []
    };

    public dataMapper: Map<string, string> = new Map<string, string>([
        ["seqTypeList", this.constants.SERVICE_NAME.GET_SEQ_TYPE_LIST]
    ]);

    constructor(
        private dataService: DataService,
        private msg: MessageCode,
        private fb: FormBuilder,
        public constants: Constants,
        private util: UtilService,
        public bsModalRef: BsModalRef,
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
        console.log("I:--START--:--OnLoad VariantCallFormModalComponent--");
        this.submitted = false;
        this.buildForm();

        this.dataMapper.forEach((value: string, key: string) => {
            this.dataService.connect(`${this.constants.CTX.SECURITY}/${this.constants.CTX.MASTER_DATA_MANAGEMENT}`, value, {}).then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl[key] = res.data.items;
                }
            });
        });

        this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_SAMPLE_SOURCE_LIST, this.appState.patient).then((res: any) => {
            if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                this.ddl.sampleSourceList = res.data.items;
            }
        });
    }

    private buildForm(): void {
        this.variantCallForm = this.fb.group({
            id: [""],
            seqType: ["", [ValidationService.requiredValidator]],
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

        if (this.variantCallForm.valid && this.validateFile()) {
            const ctx = this.constants.CTX.SECURITY;
            const serviceName = this.constants.SERVICE_NAME.UPLOAD_SAMPLE_VCF;
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
        input.append('importFile', this.variantCallForm.value.importFile);
        return input;
    }

    public handleFileSelect(files: any): void {
        this.errorMessage = undefined;
        // const files = evt.target.files;
        this.file = files[0];
        const isFileUploadValid = this.validateFile();
        if (isFileUploadValid) {
            this.variantCallForm.get('importFile').setValue(this.file);
        }
    }

    private get req() {
        const sampleVcf: any = {};
        sampleVcf.patient = this.appState.patient;
        sampleVcf.requestedUserId = this.appState.userInfo.userId;
        sampleVcf.biospecimen = { id: this.variantCallForm.value.sampleSource };

        if (this.ddl.seqTypeList) {
            sampleVcf.sequenceType = this.ddl.seqTypeList.find((x: any) => x.code === this.variantCallForm.value.seqType);
        }
        if (!this.util.isNullOrEmpty(this.variantCallForm.value.id)) {
            sampleVcf.id = this.variantCallForm.value.id;
        }

        console.log('---> sampleVcf : ', sampleVcf);
        return sampleVcf;
    }

    public deleteAttachment() {
        this.errorMessage = undefined;
        this.modalConfig = { title: 'Message', message: 'Confirm delete item?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            if (confirm) {
                this.file = undefined;
                this.variantCallForm.get('importFile').setValue(null);
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
