import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from "@angular/core";
import { FormBuilder, FormGroup, FormControl } from "@angular/forms";
import { Constants } from "../../../../services/constants";
import { ValidationService } from "../../../../services/validation.service";
import { BsModalRef } from "ngx-bootstrap/modal";
import { Subject } from "rxjs";
import { MessageCode } from "../../../../services/message-code";
import { UtilService } from "../../../../services/util.service";
import { ModalService } from "../../../../services/modal.service";
import { AlertDialogComponent } from "../../../../components/modal-dialog/alert-dialog/alert-dialog.component";
import { DataService } from "../../../../services/data.service";
import { AppStateService } from "../../../../services/app-state.service";

@Component({
    selector: "app-dynamic-content-modal",
    templateUrl: "./dynamic-content-modal.component.html",
    styleUrls: ["./dynamic-content-modal.component.scss"]
})
export class DynamicContentModalComponent implements OnInit, AfterViewInit {

    public submitted: boolean;
    public dynContentForm: FormGroup;
    public onClose: Subject<any>;
    public selected: any;
    private modalConfig: any;
    public errorMessage: string;

    public ddl: any = {
        statusList: [ this.constants.STATUS_ACTIVE, this.constants.STATUS_INACTIVE ]
    };

    constructor(
        public fb: FormBuilder,
        public constants: Constants,
        public bsModalRef: BsModalRef,
        public msg: MessageCode,
        public util: UtilService,
        private cd: ChangeDetectorRef,
        private modalService: ModalService,
        private dataService: DataService,
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
            values.analysisName = this.selected.analysisName;
            values.componentName = this.selected.componentName;
            values.inputField = this.selected.inputField;
            values.moduleName = this.selected.moduleName;
            values.modulePath = this.selected.modulePath;
            values.status = this.selected.status;

            this.dynContentForm.patchValue(values);
            this.cd.detectChanges();
        }
    }

    private init(): void {
        this.buildForm();
    }

    private buildForm(): void {
        this.dynContentForm = this.fb.group({
            id: new FormControl(""),
            analysisName: new FormControl("", [ValidationService.requiredValidator]),
            componentName: new FormControl("", [ValidationService.requiredValidator]),
            inputField: new FormControl(""),
            moduleName: new FormControl("", [ValidationService.requiredValidator]),
            modulePath: new FormControl("", [ValidationService.requiredValidator]),
            status: new FormControl("", [ValidationService.requiredValidator])
        });
    }

    public trackByFn(index, item) {
        return index;
    }

    private clearInputError(inputName: string): void {
        this.util.clearInputError(this.dynContentForm, inputName);
    }

    private addInputError(inputName: string, errors: any): void {
        this.util.addInputError(this.dynContentForm, inputName, errors);
    }

    public checkDupeComponentName(): void {
        this.clearInputError('componentName');

        if (!this.util.isNullOrEmpty(this.dynContentForm.value.componentName)) {
            const req: any = {};
            req.componentName = this.dynContentForm.value.componentName;

            if (!this.util.isNullOrEmpty(this.dynContentForm.value.id)) {
                req.id = this.dynContentForm.value.id;
            }

            this.dataService
                .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.FIND_DUPLICATE_COMPONENT_NAME, req)
                .then((res: any) => {
                    console.log("---> res: ", res);

                    if (res.responseStatus.responseCode !== this.msg.SUCCESS.code) {

                        if (res.responseStatus.responseCode === this.msg.ERROR_DUPLICATED.code) {
                            this.addInputError('componentName', {'duplicated': true});
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

        if (this.dynContentForm.valid) {
            console.log('---> Save Dynamic Content');

            const ctx = this.constants.CTX.SECURITY;
            const serviceName = this.constants.SERVICE_NAME.SAVE_DYNAMIC_CONTENT;
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

    private get req(): any {
        const dynContent: any = {};
        dynContent.id = this.dynContentForm.value.id;
        dynContent.analysisName = this.dynContentForm.value.analysisName;
        dynContent.componentName = this.dynContentForm.value.componentName;
        dynContent.inputField = this.dynContentForm.value.inputField;
        dynContent.moduleName = this.dynContentForm.value.moduleName;
        dynContent.modulePath = this.dynContentForm.value.modulePath;
        dynContent.requestedUserId = this.appState.userInfo.userId;
        dynContent.status = this.dynContentForm.value.status;
        return dynContent;
    }
}
