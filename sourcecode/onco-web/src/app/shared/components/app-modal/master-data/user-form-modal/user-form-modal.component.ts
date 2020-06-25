import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from "@angular/core";
import { UtilService } from "../../../../services/util.service";
import { Subject } from "rxjs";
import { BsModalRef } from "ngx-bootstrap/modal";
import { FormGroup, FormBuilder, FormControl, FormArray } from '@angular/forms';
import { AlertDialogComponent } from "../../../../components/modal-dialog/alert-dialog/alert-dialog.component";
import { ModalService } from "../../../../services/modal.service";
import { ValidationService } from "../../../../services/validation.service";
import { Constants } from "../../../../services/constants";
import { DataService } from "../../../../services/data.service";
import { MessageCode } from "../../../../services/message-code";
import { ConfirmDialogComponent } from "../../../../components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { TranslateService } from '@ngx-translate/core';
import { AppStateService } from '../../../../services/app-state.service';

@Component({
    selector: "app-user-form-modal",
    templateUrl: "./user-form-modal.component.html",
    styleUrls: ["./user-form-modal.component.scss"]
})
export class UserFormModalComponent implements OnInit, AfterViewInit {

    public userForm: FormGroup;
    public submitted = false;
    public onClose: Subject<any>;
    private modalConfig: any;
    public errorMessage: string;
    public selected: any;
    public roles: any;

    public ddl: any = {
        statusList: [ this.constants.STATUS_ACTIVE, this.constants.STATUS_INACTIVE ]
    };

    constructor(
        private fb: FormBuilder,
        public constants: Constants,
        public dataService: DataService,
        public msg: MessageCode,
        public util: UtilService,
        private modalService: ModalService,
        private cd: ChangeDetectorRef,
        public bsModalRef: BsModalRef,
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
            values.id = this.selected.id;
            values.username = this.selected.username;
            values.status = this.selected.status;
            this.userForm.patchValue(values);
            this.cd.detectChanges();
        }
    }

    private init(): void {
        console.log("I:--START--:--OnLoad UserFormModalComponent--");
        this.submitted = false;
        this.buildForm();

        this.getRoles().then((res: any) => {
            if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                this.roles = res.data.items;
                this.addCheckboxes();
                this.onPasswordChanged();
            } else {
                this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
            }
        });
    }

    private buildForm(): void {
        this.userForm = this.fb.group({
            id: new FormControl(""),
            firstName: new FormControl("", [ValidationService.requiredValidator]),
            lastName: new FormControl("", [ValidationService.requiredValidator]),
            username: new FormControl("", [ValidationService.requiredValidator]),
            password: new FormControl("", [ValidationService.requiredValidator]),
            confirmPassword: new FormControl("", [ValidationService.requiredValidator]),
            status: new FormControl("", [ValidationService.requiredValidator]),
            roles: new FormArray([])
        });
    }

    private getRoles(): Promise<{}> {
        const p = new Promise(resolve => {
            const ctx = this.constants.CTX.SECURITY;
            const serviceName = this.constants.SERVICE_NAME.GET_ACTIVE_ROLES;
            this.dataService.connect(ctx, serviceName, {}).then((res: any) => {
                resolve(res);
            });
        });

        return p;
    }

    private addCheckboxes(): void {
        if (this.roles) {
            this.roles.forEach((o: any, i: any) => {
                // const control = new FormControl(i === 0); // if first item set to true, else false
                const control = new FormControl();
                (this.userForm.get('roles') as FormArray).push(control);
            });
        }
    }

    public trackByFn(index, item) {
        return index;
    }

    private clearInputError(inputName: string): void {
        this.util.clearInputError(this.userForm, inputName);
    }

    private addInputError(inputName: string, errors: any): void {
        this.util.addInputError(this.userForm, inputName, errors);
    }

    public checkDupeUsername(): void {
        this.clearInputError('username');

        if (!this.util.isNullOrEmpty(this.userForm.value.username)) {
            const req: any = {};
            req.username = this.userForm.value.username;

            if (!this.util.isNullOrEmpty(this.userForm.value.id)) {
                req.id = this.userForm.value.id;
            }

            this.dataService
                .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.FIND_DUPLICATE_USERNAME, req)
                .then((res: any) => {
                    console.log("---> res: ", res);

                    if (res.responseStatus.responseCode !== this.msg.SUCCESS.code) {

                        if (res.responseStatus.responseCode === this.msg.ERROR_DUPLICATED.code) {
                            this.addInputError('username', {'duplicated': true});
                        }

                        this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                        this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
                    }
                });
        }
    }

    public onSave(): void {
        this.submitted = true;
        this.errorMessage = undefined;

        if (this.userForm.valid) {
            const ctx = this.constants.CTX.SECURITY;
            const serviceName = this.constants.SERVICE_NAME.SAVE_USER;
            this.dataService.connect(ctx, serviceName, this.req).then((res: any) => {
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

    public onPasswordChanged(): void {
        const ctrlUserId = this.userForm.get('id');
        const ctrlPasswd = this.userForm.get('password');
        const ctrlConfirmPasswd = this.userForm.get("confirmPassword");

        if (!this.util.isNullOrEmpty(ctrlUserId.value)) {
            if (!this.util.isNullOrEmpty(ctrlPasswd.value) || !this.util.isNullOrEmpty(ctrlConfirmPasswd.value)) {
                ctrlPasswd.setValidators([ValidationService.requiredValidator]);
                ctrlPasswd.updateValueAndValidity();

                ctrlConfirmPasswd.setValidators([ValidationService.requiredValidator]);
                ctrlConfirmPasswd.updateValueAndValidity();
            } else {
                ctrlPasswd.clearValidators();
                ctrlPasswd.updateValueAndValidity();

                ctrlConfirmPasswd.clearValidators();
                ctrlConfirmPasswd.updateValueAndValidity();
            }
        }
    }

    private get req(): any {
        const user: any = {};
        return user;
    }
}
