import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from "@angular/core";
import { UtilService } from "../../../../services/util.service";
import { Subject } from "rxjs";
import { BsModalRef } from "ngx-bootstrap/modal";
import { FormGroup, FormBuilder, FormControl } from '@angular/forms';
import { AlertDialogComponent } from "../../../../components/modal-dialog/alert-dialog/alert-dialog.component";
import { ModalService } from "../../../../services/modal.service";
import { ValidationService } from "../../../../services/validation.service";
import { DateService } from "../../../../services/date.service";
import { Constants } from "../../../../services/constants";
import { DataService } from "../../../../services/data.service";
import { MessageCode } from "../../../../services/message-code";
import { ConfirmDialogComponent } from "../../../../components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { UploadService } from '../../../../services/upload.service';
import { TranslateService } from '@ngx-translate/core';
import { AppStateService } from '../../../../services/app-state.service';
@Component({
    selector: "app-icd-form-modal",
    templateUrl: "./icd-form-modal.component.html",
    styleUrls: ["./icd-form-modal.component.scss"]
})
export class IcdFormModalComponent implements OnInit, AfterViewInit {

    public icdForm: FormGroup;
    public submitted = false;
    public onClose: Subject<any>;
    private modalConfig: any;
    public errorMessage: string;
    public selected: any;
    public minDate: any;
    public dt: Date;

    public file: any;
    private validFileExtensions = [".csv"];

    constructor(
        private fb: FormBuilder,
        private dateService: DateService,
        public constants: Constants,
        public dataService: DataService,
        public msg: MessageCode,
        public util: UtilService,
        private modalService: ModalService,
        private cd: ChangeDetectorRef,
        public bsModalRef: BsModalRef,
        private uploadService: UploadService,
        private translate: TranslateService,
        private appState: AppStateService
    ) {
        this.onClose = new Subject();
    }

    ngOnInit(): void {
        this.init();
    }

    ngAfterViewInit(): void {
        if (this.selected) {
            const values: any = {};
            values.dataVerId = this.selected.id;
            values.version = this.selected.version;
            values.dataDate = this.selected.dataDate;
            values.markAsDefault = (this.selected.isDefault === this.constants.YES);
            this.icdForm.patchValue(values);
            this.cd.detectChanges();
        }
    }

    private init(): void {
        console.log("I:--START--:--OnLoad IcdFormModalComponent--");
        this.submitted = false;
        this.buildForm();

        this.dt = this.dateService.getCurrentDate();
        this.minDate = this.dateService.parseDate('1900-01-01');
    }

    private buildForm(): void {
        this.icdForm = this.fb.group({
            dataVerId: new FormControl(""),
            version: new FormControl("", [ValidationService.requiredValidator]),
            dataDate: new FormControl(null, [ValidationService.requiredValidator]),
            markAsDefault: new FormControl(false, [ValidationService.requiredValidator]),
            importFile: new FormControl(null)
        });
    }

    public trackByFn(index, item) {
        return index;
    }

    private clearInputError(inputName: string): void {
        this.util.clearInputError(this.icdForm, inputName);
    }

    private addInputError(inputName: string, errors: any): void {
        this.util.addInputError(this.icdForm, inputName, errors);
    }

    public isDateInvalid(inputName: string) {
        this.clearInputError(inputName);
        const dt = this.icdForm.value[inputName];

        if (this.util.isNullOrEmpty(dt) || dt.toString() === 'Invalid Date') {
            this.clearInputValue(inputName);
        } else {
            const year = Number(dt.getFullYear());
            if (year < 1900) {
                this.addInputError(inputName, {'invalidDate': true});
            }
        }
    }

    public checkDuplicateVersion(): void {
        this.clearInputError('version');

        if (!this.util.isNullOrEmpty(this.icdForm.value.version)) {
            const req: any = {};
            req.type = 'ICD';
            req.id = this.icdForm.value.dataVerId;
            req.version = this.icdForm.value.version;

            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.FIND_DUPLICATE_DATA_VERSION, req)
                .then((res: any) => {
                    console.log("---> res: ", res);

                    if (res.responseStatus.responseCode !== this.msg.SUCCESS.code) {

                        if (res.responseStatus.responseCode === this.msg.ERROR_DUPLICATED.code) {
                            this.addInputError('version', {'duplicated': true});
                        }

                        this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                        this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
                    }
                });
        }
    }

    private clearInputValue(inputName: string): void {
        const control: any = {};
        control[inputName] = null;
    }

    public onSave(): void {
        this.submitted = true;
        this.errorMessage = undefined;

        if (this.icdForm.valid && this.validateFile()) {
            console.log('---> Save ICD');

            // Display the key/value pairs
            // this.formData.forEach((value, key) => {
            //     console.log(`${key}-${value}`);
            // });

            const ctx = this.constants.CTX.SECURITY;
            const serviceName = this.constants.SERVICE_NAME.UPLOAD_ICD;
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
        input.append('importFile', this.icdForm.value.importFile);
        return input;
    }

    public handleFileSelect(files: any): void {
        this.errorMessage = undefined;
        // const files = evt.target.files;
        this.file = files[0];
        const isFileUploadValid = this.validateFile();
        if (isFileUploadValid) {
            this.icdForm.get('importFile').setValue(this.file);
        }
    }

    private get req() {
        const dataVersion: any = {};
        dataVersion.id = this.icdForm.value.dataVerId;
        dataVersion.version = this.icdForm.value.version;
        dataVersion.dataDate = this.icdForm.value.dataDate;
        dataVersion.isDefault = this.icdForm.value.markAsDefault ? this.constants.YES : this.constants.NO;
        dataVersion.type = 'ICD';
        dataVersion.requestedUserId = this.appState.userInfo.userId;
        console.log('---> dataVersion : ', dataVersion);
        return dataVersion;
    }

    public deleteAttachment() {
        this.errorMessage = undefined;
        this.modalConfig = { title: 'Message', message: 'Confirm delete item?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            if (confirm) {
                this.file = undefined;
                this.icdForm.get('importFile').setValue(null);
            }
        });
    }

    private validateFile(): boolean {

        if (!this.isEdit) {
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
        }

        return true;
    }

    public get isEdit(): boolean {
        return !this.util.isNullOrEmpty(this.icdForm.value.dataVerId);
    }
}
