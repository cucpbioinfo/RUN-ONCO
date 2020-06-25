import { Component, OnInit, ChangeDetectorRef, AfterViewInit } from "@angular/core";
import { DataService } from "../../../shared/services/data.service";
import { AppStateService } from "../../../shared/services/app-state.service";
import { MessageCode } from "../../../shared/services/message-code";
import { ValidationService } from "../../../shared/services/validation.service";
import { FormBuilder } from "@angular/forms";
import { Constants } from "../../../shared/services/constants";
import { UtilService } from "../../../shared/services/util.service";
import { Subject, Observable } from "rxjs";
import { TypeaheadMatch } from "ngx-bootstrap/typeahead";
import { ModalService } from "../../../shared/services/modal.service";
import { AlertDialogComponent } from "../../../shared/components/modal-dialog/alert-dialog/alert-dialog.component";
import { Router } from "@angular/router";
import { DateService } from "../../../shared/services/date.service";
import { Dictionary } from '../../../shared/bean/dictionary';

@Component({
    selector: "app-biospecimen-form",
    templateUrl: "./biospecimen-form.component.html",
    styleUrls: ["./biospecimen-form.component.scss"]
})
export class BiospecimenFormComponent implements OnInit, AfterViewInit {

    public specimenForm: any;
    public onClose: Subject<any>;
    public formValues: any;
    public submitted: boolean;
    public breadcrumbs: any;
    private modalConfig: any;
    public pageComponent: string;
    public minDate: any;
    public dt: Date;
    public prevPathoNo: string;
    public temp: Dictionary = {};

    typeaheadLoading: boolean;
    noResult = true;
    dataSource: Observable<any>;
    pathoList: any;

    public isShowBloodType = false;
    public isShowTissueType = false;
    public isShowCellType = false;

    private allSampleType = {
        '01': ['bloodTypeCode'],
        '02': ['tissueTypeCode', 'pathoId'],
        '03': ['cellTypeCode', 'cellLineTypeCode']
    };

    public ddl: any = {
        sampleTypeList: [],
        bloodTypeList: [],
        tissueTypeList: [],
        cellTypeList: [],
        cellLineTypeList: [],
        freezeMethodList: [],
        preserveMethodList: []
    };

    public dataMapper: Map<string, string> = new Map<string, string>([
        ["sampleTypeList", this.constants.SERVICE_NAME.GET_SAMPLE_TYPE_LIST],
        ["bloodTypeList", this.constants.SERVICE_NAME.GET_BLOOD_TYPE_LIST],
        ["tissueTypeList", this.constants.SERVICE_NAME.GET_TISSUE_TYPE_LIST],
        ["cellTypeList", this.constants.SERVICE_NAME.GET_CELL_TYPE_LIST],
        ["cellLineTypeList", this.constants.SERVICE_NAME.GET_CELL_LINE_TYPE_LIST],
        ["freezeMethodList", this.constants.SERVICE_NAME.GET_FREEZE_METHOD_LIST],
        ["preserveMethodList", this.constants.SERVICE_NAME.GET_PRESERVE_METHOD_LIST]
    ]);

    constructor(
        public dataService: DataService,
        public appState: AppStateService,
        public msg: MessageCode,
        public fb: FormBuilder,
        public constants: Constants,
        public cd: ChangeDetectorRef,
        public util: UtilService,
        private modalService: ModalService,
        private router: Router,
        private dateService: DateService
    ) {
        this.onClose = new Subject();

        this.dataSource = Observable.create((observer: any) => {

            const req = {
                patient: {
                    id: this.formValues.patient.id
                },
                patho: {
                    pathologyNo: this.specimenForm.value.pathoNo,
                },
                paging: { startIndex: 0, fetchSize: 10 }
            };

            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.SEARCH_PATIENT_PATHO_BY_PATHO_NO, req, true).then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.pathoList = res.data;

                    const names = [];
                    res.data.forEach((x: any) => {
                        names.push(x.pathologyNo);
                    });
                    observer.next(names);
                } else {
                    observer.next();
                }

                this.prevPathoNo = this.specimenForm.value.pathoNo;
            });
        });
    }

    ngOnInit(): void {
        this.init();
    }

    ngAfterViewInit(): void {
        if (this.formValues) {
            const values: any = {};
            values.id = this.formValues.id;
            values.initialWeight = this.formValues.initialWeight || '';
            values.ref = this.formValues.ref;
            values.status = this.formValues.status;

            if (!this.util.isNullOrEmpty(this.formValues.collectSampleDate)) {
                values.collectSampleDate = new Date(this.formValues.collectSampleDate);
            }
            if (!this.util.isNullOrEmpty(this.formValues.freezeDate)) {
                values.freezeDate = new Date(this.formValues.freezeDate);
            }
            if (this.formValues.patient) {
                values.patientId = this.formValues.patient.id;
            }
            if (this.formValues.pathological) {
                const patho = this.formValues.pathological;
                values.pathoId = patho.id;
                values.pathoNo = patho.pathologyNo;
            }
            if (this.formValues.sampleType) {
                values.sampleTypeCode = this.formValues.sampleType.code;
            }
            if (this.formValues.bloodType) {
                values.bloodTypeCode = this.formValues.bloodType.code;
            }
            if (this.formValues.tissueType) {
                values.tissueTypeCode = this.formValues.tissueType.code;
            }
            if (this.formValues.cellType) {
                values.cellTypeCode = this.formValues.cellType.code;
            }
            if (this.formValues.cellLineType) {
                values.cellLineTypeCode = this.formValues.cellLineType.code;
            }
            if (this.formValues.freezeMethod) {
                values.freezeMethodCode = this.formValues.freezeMethod.code;
            }
            if (this.formValues.preserveMethod) {
                values.preserveMethodCode = this.formValues.preserveMethod.code;
            }

            this.specimenForm.patchValue(values);
            this.onSelectedChanged();
        }

        this.cd.detectChanges();
    }

    public get biospecimen() {
        const data: any = {};

        const sampleTypeCode = this.specimenForm.value.sampleTypeCode;
        if (!this.util.isNullOrEmpty(sampleTypeCode)) {
            data.sampleType = this.ddl.sampleTypeList.find((x: any) => x.code === sampleTypeCode);
        }

        const bloodTypeCode = this.specimenForm.value.bloodTypeCode;
        if (!this.util.isNullOrEmpty(bloodTypeCode)) {
            data.bloodType = this.ddl.bloodTypeList.find((x: any) => x.code === bloodTypeCode);
        }

        const tissueTypeCode = this.specimenForm.value.tissueTypeCode;
        if (!this.util.isNullOrEmpty(tissueTypeCode)) {
            data.tissueType = this.ddl.tissueTypeList.find((x: any) => x.code === tissueTypeCode);
        }

        const cellTypeCode = this.specimenForm.value.cellTypeCode;
        if (!this.util.isNullOrEmpty(cellTypeCode)) {
            data.cellType = this.ddl.cellTypeList.find((x: any) => x.code === cellTypeCode);
        }

        const cellLineTypeCode = this.specimenForm.value.cellLineTypeCode;
        if (!this.util.isNullOrEmpty(cellLineTypeCode)) {
            data.cellLineType = this.ddl.cellLineTypeList.find((x: any) => x.code === cellLineTypeCode);
        }

        const freezeMethodCode = this.specimenForm.value.freezeMethodCode;
        if (!this.util.isNullOrEmpty(freezeMethodCode)) {
            data.freezeMethod = this.ddl.freezeMethodList.find((x: any) => x.code === freezeMethodCode);
        }

        const preserveMethodCode = this.specimenForm.value.preserveMethodCode;
        if (!this.util.isNullOrEmpty(preserveMethodCode)) {
            data.preserveMethod = this.ddl.preserveMethodList.find((x: any) => x.code === preserveMethodCode);
        }

        const patientId = this.specimenForm.value.patientId;
        if (!this.util.isNullOrEmpty(patientId)) {
            data.patient = {};
            data.patient.id = patientId;
        }

        const pathoId = this.specimenForm.value.pathoId;
        if (!this.util.isNullOrEmpty(pathoId)) {
            data.pathological = {};
            data.pathological.id = pathoId;
        }

        data.initialWeight = this.specimenForm.value.initialWeight;
        data.collectSampleDate = this.temp['collectSampleDate'];
        data.freezeDate = this.temp['freezeDate'];
        data.status = this.specimenForm.value.status;
        data.ref = this.specimenForm.value.ref;
        data.id = this.specimenForm.value.id;
        return data;
    }

    private init(): void {
        console.log("I:--START--:--OnLoad InputBiospecimenComponent--");

        this.buildForm();

        this.dt = this.dateService.getCurrentDate();
        this.minDate = this.dateService.parseDate('1900-01-01');

        this.breadcrumbs = [
            {
                name: 'Search',
                url: '/biospecimen-search',
                active: false
            },
            {
                name: 'Biospecimen Form',
                url: '/biospecimen-form',
                active: true
            }
        ];

        this.pageComponent = 'biospecimen-search';

        if (this.appState.biospecimen) {
            this.formValues = this.appState.biospecimen;
        }

        this.dataMapper.forEach((value: string, key: string) => {
            const criteria: any = this.getMFilter(key);

            this.dataService.connect(`${this.constants.CTX.SECURITY}/${this.constants.CTX.MASTER_DATA_MANAGEMENT}`, value, criteria).then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl[key] = res.data.items;
                }
            });
        });

        // Scroll to top of page
        this.util.scrollTop();
    }

    private buildForm(): void {
        this.specimenForm = this.fb.group({
            id: [""],
            patientId: [""],
            status: [""],
            ref: ["", [ValidationService.requiredValidator]],
            sampleTypeCode: ["", [ValidationService.requiredValidator]],
            bloodTypeCode: ["", [ValidationService.requiredValidator]],
            tissueTypeCode: ["", [ValidationService.requiredValidator]],
            cellTypeCode: ["", [ValidationService.requiredValidator]],
            cellLineTypeCode: [""],
            freezeMethodCode: ["", [ValidationService.requiredValidator]],
            preserveMethodCode: ["", [ValidationService.requiredValidator]],
            initialWeight: ["", [ValidationService.requiredValidator, ValidationService.initWeightValidator]],
            collectSampleDate: [null, [ValidationService.requiredValidator]],
            freezeDate: [null, [ValidationService.requiredValidator]],
            pathoId: ["", [ValidationService.requiredValidator]],
            pathoNo: [""]
        });
    }

    public changeTypeaheadLoading(e: boolean): void {
        this.typeaheadLoading = e;
    }

    public typeaheadOnSelect(e: TypeaheadMatch): void {
        const selected = this.pathoList.find((x: any) => x.pathologyNo === e.value);

        if (selected) {
            this.noResult = false;
            this.specimenForm.patchValue({pathoId: selected.id});
        }
    }

    public typeaheadNoResults(event: any): void {
        this.noResult = event;

        if (this.noResult) {
            this.specimenForm.patchValue({pathoId: ''});
        }
        console.log('---> this.noResult : ', this.noResult);
    }

    public onTextChanged(): void {
        if (!this.util.isEmptyObject(this.specimenForm.value.pathoNo)) {
            this.specimenForm.patchValue({pathoId: ''});
        }
    }

    private getMFilter(ctrlName: any): any {
        let criteria: any = {};

        if (ctrlName === 'tissueTypeList') {
            criteria = { type: 2 };
        }

        return criteria;
    }

    public trackByFn(index, item) {
        return index;
    }

    public onSave(): void {
        this.submitted = true;

        if (this.specimenForm.valid) {
            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.SAVE_BIOSPECIMEN, this.biospecimen).then((res: any) => {
                console.log('---> res: ', res);
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.router.navigate(['/biospecimen-search']);
                } else {
                    this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                    this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
                }
            });
        }
    }

    public onCancel(): void {
        this.clearAppState();
        this.router.navigate(["biospecimen-search"]);
    }

    public onSelectedChanged(): void {
        console.log('---> sampleTypeCode : ',  this.specimenForm.value.sampleTypeCode);
        const sampleTypeCode = this.specimenForm.value.sampleTypeCode;

        // Reset isShow flags
        this.isShowBloodType = false;
        this.isShowTissueType = false;
        this.isShowCellType = false;

        // Clear the validation
        ['01', '02', '03'].forEach(x => {
            const fields = this.allSampleType[x];
            fields.forEach((f: any) => {
                this.specimenForm.get(f).clearValidators();
                this.specimenForm.get(f).updateValueAndValidity();
            });
        });

        if (sampleTypeCode === this.constants.SAMPLE_TYPE.BLOOD) {
            this.isShowBloodType = true;
        } else if (sampleTypeCode === this.constants.SAMPLE_TYPE.TISSUE) {
            this.isShowTissueType = true;
        } else if (sampleTypeCode === this.constants.SAMPLE_TYPE.CELL) {
            this.isShowCellType = true;
        }

        if (!this.util.isNullOrEmpty(sampleTypeCode)) {
            this.allSampleType[sampleTypeCode].forEach((f: any) => {
                this.specimenForm.get(f).setValidators([ValidationService.requiredValidator]);
                this.specimenForm.get(f).updateValueAndValidity();
            });
        }
    }

    public checkDuplicateRefNo(): void {
        this.clearInputError('ref');

        if (!this.util.isNullOrEmpty(this.specimenForm.value.ref)) {
            const req: any = {};
            req.ref = this.specimenForm.value.ref;

            if (!this.util.isNullOrEmpty(this.specimenForm.value.id)) {
                req.id = this.specimenForm.value.id;
            }

            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.FIND_DUPLICATE_BIO_REF, req)
                    .then((res: any) => {
                        console.log("---> res: ", res);
                        if (res.responseStatus.responseCode !== this.msg.SUCCESS.code) {

                            if (res.responseStatus.responseCode === this.msg.ERROR_DUPLICATED.code) {
                                this.addInputError('ref', {'duplicated': true});
                            }

                            this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                            this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
                        }
                    });
        }
    }

    private clearInputError(inputName: string): void {
        this.util.clearInputError(this.specimenForm, inputName);
    }

    private addInputError(inputName: string, errors: any): void {
        this.util.addInputError(this.specimenForm, inputName, errors);
    }

    public get patient() {
        return this.appState.biospecimen.patient;
    }

    public get isHidden(): boolean {
        return !this.patient;
    }

    public onHnChanged(obj: any): void {
        this.appState.biospecimen.patient = obj.patient;
        this.specimenForm.patchValue({ patientId: obj.patient.id });
    }

    private clearAppState(): void {
        delete this.appState.biospecimen;
    }

    public isDateInvalid(inputName: string) {
        this.clearInputError(inputName);
        const dt = this.specimenForm.value[inputName];

        if (this.util.isNullOrEmpty(dt) || dt.toString() === 'Invalid Date') {
            this.clearInputValue(inputName);
        } else {
            const year = Number(dt.getFullYear());
            if (year < 1900) {
                this.addInputError(inputName, {'invalidDate': true});
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
        this.specimenForm.patchValue(control);
    }
}
