import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from "@angular/core";
import { FormBuilder, FormGroup } from "@angular/forms";
import { Constants } from "../../../shared/services/constants";
import { ValidationService } from "../../../shared/services/validation.service";
import { MessageCode } from "../../../shared/services/message-code";
import { DataService } from "../../../shared/services/data.service";
import { UtilService } from "../../../shared/services/util.service";
import { AppStateService } from "../../../shared/services/app-state.service";
import { Router } from "@angular/router";
import { ModalService } from "../../../shared/services/modal.service";
import { SurvivalFollowupFormModalComponent } from "../../../shared/components/app-modal/clinical-data/survival-followup-form-modal/survival-followup-form-modal.component";
import { CurrencyFormatterPipe } from "../../../shared/pipes/currency-formatter.pipe";
import { AlertDialogComponent } from "../../../shared/components/modal-dialog/alert-dialog/alert-dialog.component";
import { DateService } from "../../../shared/services/date.service";
import { Dictionary } from 'src/app/shared/bean/dictionary';

@Component({
    selector: "app-patient-form",
    templateUrl: "./patient-form.component.html",
    styleUrls: ["./patient-form.component.scss"]
})
export class PatientFormComponent implements OnInit, AfterViewInit {
    public submitted: boolean;
    public patientForm: FormGroup;
    public minDate: any;
    public maxDate: any;
    public dt: Date;
    public tab: any;
    public prevTab: any;
    private modalConfig: any;
    private formValues: any;
    public breadcrumbs: any;
    public temp: Dictionary = {};

    public ddl: any = {
        raceList: [],
        genderList: []
    };

    private dataMapper: Map<string, string> = new Map<string, string>([
        ["raceList", this.constants.SERVICE_NAME.GET_RACE_LIST],
        ["genderList", this.constants.SERVICE_NAME.GET_GENDER_LIST]
    ]);

    constructor(
        public fb: FormBuilder,
        public constants: Constants,
        public dataService: DataService,
        public msg: MessageCode,
        public util: UtilService,
        public appState: AppStateService,
        private router: Router,
        private modalService: ModalService,
        private cd: ChangeDetectorRef,
        private currencyFormatter: CurrencyFormatterPipe,
        private dateService: DateService
    ) {}

    ngOnInit(): void {
        this.init();
    }

    ngAfterViewInit(): void {
        if (this.formValues) {
            const values: any = {};
            values.id = this.formValues.id;
            values.hn = this.formValues.hn;
            values.weight = this.currencyFormatter.transform(this.formValues.weight);
            values.height = this.currencyFormatter.transform(this.formValues.height);

            if (this.formValues.birthDate) {
                values.birthDate = new Date(this.formValues.birthDate);
            }
            if (this.formValues.gender) {
                values.genderCode = this.formValues.gender.code;
            }
            if (this.formValues.race) {
                values.raceCode = this.formValues.race.code;
            }

            this.patientForm.patchValue(values);
        }

        this.cd.detectChanges();
    }

    public onSave() {
        this.submitted = true;

        if (this.patientForm.valid) {
            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.SAVE_PATIENT, this.patient)
                .then((res: any) => {
                    console.log("---> res: ", res);

                    if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                        this.modalConfig = { title: "Message", message: "Save successfully." };
                        this.modalService.show(AlertDialogComponent, "modal-sm", this.modalConfig)
                            .subscribe(() => {
                                this.router.navigate(["/patient-search"]);
                            });
                    } else {
                        this.modalConfig = { title: "Message", message: res.responseStatus.responseMessage };
                        this.modalService.show(AlertDialogComponent, "modal-sm", this.modalConfig);
                    }
                });
        }
    }

    private init(): void {
        this.submitted = false;
        this.formValues = this.appState.patient;

        this.buildForm();

        this.dt = this.dateService.getCurrentDate();
        this.maxDate = this.dt;
        this.minDate = this.dateService.parseDate("1900-01-01");

        this.tab = {
            data: [{ value: 1, name: "lbl.survivalFollowup" }],
            step: 1,
            styleClass: "tablist"
        };

        if (this.isShowTab) {
            this.tab.data.push({ value: 2, name: "lbl.variantCall" });
            this.tab.data.push({ value: 3, name: "lbl.rnaSeq" });
        }

        this.prevTab = this.tab.step;

        this.breadcrumbs = [
            {
                name: "Search",
                url: "/patient-search",
                active: false
            },
            {
                name: "Patient Form",
                url: "/patient-form",
                active: true
            }
        ];

        console.log(`I:--START--:--Init Patient Form--:minDate/${this.minDate}:maxDate/${this.maxDate}`);

        this.dataMapper.forEach((value: string, key: string) => {
            if (this.appState.data[key]) {
                this.ddl[key] = this.appState.data[key];
            } else {
                this.dataService.connect(`${this.constants.CTX.SECURITY}/${this.constants.CTX.MASTER_DATA_MANAGEMENT}`, value, {})
                    .then((res: any) => {
                        if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                            this.ddl[key] = res.data.items;
                            this.appState.data[key] = this.ddl[key];
                        }
                    });
            }
        });
    }

    private buildForm(): void {
        this.patientForm = this.fb.group({
            id: [""],
            hn: ["", [ValidationService.requiredValidator]],
            genderCode: ["", [ValidationService.requiredValidator]],
            birthDate: [null, [ValidationService.requiredValidator]],
            weight: [
                "",
                [
                    ValidationService.requiredValidator,
                    ValidationService.weightValidator
                ]
            ],
            height: [
                "",
                [
                    ValidationService.requiredValidator,
                    ValidationService.heightValidator
                ]
            ],
            raceCode: ["", [ValidationService.requiredValidator]],
            status: [""]
        });
    }

    public trackByFn(index, item) {
        return index;
    }

    public editItem(idx: number, item: any) {
        this.modalConfig = { selected: item };
        console.log("I:--START--:--InitEdit SurvivalFollowup--:modalConfig/", this.modalConfig);
        const modalRef = this.modalService.show(SurvivalFollowupFormModalComponent, "modal-lg", this.modalConfig);
        modalRef.subscribe((patho: any) => {
            this.appState.patient.survivalFollowupList[idx] = patho;
        });
    }

    public deleteItem(idx: number) {
        this.modalConfig = { title: "Message", message: "Confirm delete item?" };
        const modalRef = this.modalService.show(SurvivalFollowupFormModalComponent, "modal-sm", this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            console.log("---> confirm : ", confirm);

            if (confirm) {
                if (this.appState.patient.survivalFollowupList && this.appState.patient.survivalFollowupList[idx]) {
                    const survivalFollowup = this.appState.patient.survivalFollowupList[idx];

                    if (!this.util.isNullOrEmpty(survivalFollowup.id)) {
                        survivalFollowup.status = this.constants.STATUS_INACTIVE;
                    } else {
                        this.appState.patient.survivalFollowupList.splice(idx, 1);
                    }
                }
            }
        });
    }

    private get patient() {
        const data: any = this.appState.patient;
        data.hn = this.patientForm.value.hn;
        data.birthDate = this.temp['birthDate'];
        data.weight = this.currencyFormatter.parse(this.patientForm.value.weight);
        data.height = this.currencyFormatter.parse(this.patientForm.value.height);
        data.requestedUserId = this.appState.userInfo.userId;

        if (!this.util.isNullOrEmpty(this.patientForm.value.genderCode)) {
            data.gender = {};
            data.gender.code = this.patientForm.value.genderCode;
        }

        if (!this.util.isNullOrEmpty(this.patientForm.value.raceCode)) {
            data.race = {};
            data.race.code = this.patientForm.value.raceCode;
        }

        return data;
    }

    public onCancel(): void {
        this.router.navigate(["patient-search"]);
    }

    public goToNextTab(step: any) {
        this.submitted = false;
        this.prevTab = step.value;
        this.tab.step = step.value;
    }

    public get isShowTab(): boolean {
        const patient = this.appState.patient;

        if (patient && !this.util.isNullOrEmpty(patient.id)) {
            return true;
        }

        return false;
    }

    public checkDuplicateHn(): void {
        this.clearInputError("hn");

        if (!this.util.isNullOrEmpty(this.patientForm.value.hn)) {
            const req: any = {};
            req.hn = this.patientForm.value.hn;

            if (!this.util.isNullOrEmpty(this.patientForm.value.id)) {
                req.id = this.patientForm.value.id;
            }

            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.FIND_DUPLICATE_HN, req)
                .then((res: any) => {
                    console.log("---> res: ", res);

                    if (res.responseStatus.responseCode !== this.msg.SUCCESS.code) {
                        if (res.responseStatus.responseCode === this.msg.ERROR_DUPLICATED.code) {
                            this.addInputError("hn", { duplicated: true });
                        }

                        this.modalConfig = { title: "Message", message: res.responseStatus.responseMessage };
                        this.modalService.show(AlertDialogComponent, "modal-sm", this.modalConfig);
                    }
                });
        }
    }

    private clearInputError(inputName: string): void {
        this.util.clearInputError(this.patientForm, inputName);
    }

    private addInputError(inputName: string, errors: any): void {
        this.util.addInputError(this.patientForm, inputName, errors);
    }

    public isDateInvalid(inputName: string) {
        this.clearInputError(inputName);
        const dt = this.patientForm.value[inputName];

        if (this.util.isNullOrEmpty(dt) || dt.toString() === "Invalid Date") {
            this.clearInputValue(inputName);
        } else {
            const year = Number(dt.getFullYear());
            const currentYear = Number(this.dt.getFullYear());
            if (year < 1900 || year > currentYear) {
                this.addInputError(inputName, { invalidDate: true });
            }
        }
    }

    public bsValueChange(key: string, val: Date) {
        if (val) {
            this.temp[key] = new Date(val.getTime() - val.getTimezoneOffset() * 60 * 1000);
        } else {
            this.temp[key] = undefined;
        }
    }

    private clearInputValue(inputName: string): void {
        const control: any = {};
        control[inputName] = null;
        this.patientForm.patchValue(control);
    }
}
