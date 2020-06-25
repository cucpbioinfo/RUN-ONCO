import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from "@angular/core";
import { Constants } from "../../../../services/constants";
import { DataService } from "../../../../services/data.service";
import { MessageCode } from "../../../../services/message-code";
import { ValidationService } from "../../../../services/validation.service";
import { FormBuilder } from "@angular/forms";
import { UtilService } from "../../../../services/util.service";
import { Subject } from "rxjs";
import { BsModalRef } from "ngx-bootstrap/modal";
import { DateService } from "../../../../services/date.service";
import { Dictionary } from '../../../../bean/dictionary';

@Component({
    selector: "app-clinical-course-form-modal",
    templateUrl: "./clinical-course-form-modal.component.html",
    styleUrls: ["./clinical-course-form-modal.component.scss"]
})
export class ClinicalCourseFormModalComponent implements OnInit, AfterViewInit {

    public submitted = false;
    public clinicalCourseForm: any;
    public onClose: Subject<any>;
    public isRecurDateShow = false;
    public selected: any;
    public cssCol: string;
    public minDate: any;
    public dt: Date;
    public temp: Dictionary = {};

    public get clinicalCourse() {

        const data: any = {};
        data.recurrenceDate = this.temp['recurrenceDate'];
        data.recordDate = this.temp['recordDate'];
        data.status = this.clinicalCourseForm.value.status;
        data.id = this.clinicalCourseForm.value.id;

        const recurStatusCode = this.clinicalCourseForm.value.recurrenceStatusCode;

        if (!this.util.isNullOrEmpty(recurStatusCode)) {
            const recurStatus = this.ddl.recurrenceStatusList.find((x: any) => x.code === recurStatusCode);
            if (recurStatus) {
                data.recurrenceStatus = {};
                data.recurrenceStatus.code = recurStatus.code;
                data.recurrenceStatus.name = recurStatus.name;
            }
        }

        return data;
    }

    public ddl: any = {
        recurrenceStatusList: []
    };

    public dataMapper: Map<string, string> = new Map<string, string>([
        [
            "recurrenceStatusList",
            this.constants.SERVICE_NAME.GET_RECURRENCE_STATUS_LIST
        ]
    ]);

    constructor(
        private dataService: DataService,
        private msg: MessageCode,
        private fb: FormBuilder,
        public constants: Constants,
        private cd: ChangeDetectorRef,
        private util: UtilService,
        public bsModalRef: BsModalRef,
        private dateService: DateService
    ) {
        this.onClose = new Subject();
    }

    ngOnInit(): void {
        this.init();
    }

    ngAfterViewInit(): void {
        if (this.selected) {
            const values: any = {};
            values.status = this.selected.status;
            values.id = this.selected.id;

            if (this.selected.recurrenceStatus) {
                values.recurrenceStatusCode = this.selected.recurrenceStatus.code;
            }
            if (!this.util.isNullOrEmpty(this.selected.recurrenceDate)) {
                values.recurrenceDate = new Date(this.selected.recurrenceDate);
            }
            if (!this.util.isNullOrEmpty(this.selected.recordDate)) {
                values.recordDate = new Date(this.selected.recordDate);
            }
            this.clinicalCourseForm.patchValue(values);
            this.onSelectedChanged();
        }

        this.cd.detectChanges();
    }

    public onSave() {
        this.submitted = true;

        if (this.clinicalCourseForm.valid) {
            this.onClose.next(this.clinicalCourse);
            this.bsModalRef.hide();
        }
    }

    private init(): void {
        console.log('I:--START--:--OnLoad ClinicalCourseFormModal--');

        this.buildForm();
        this.cssCol = 'col-md-6';

        this.dt = this.dateService.getCurrentDate();
        this.minDate = this.dateService.parseDate('1900-01-01');

        this.dataMapper.forEach((value: string, key: string) => {
            this.dataService.connect(`${this.constants.CTX.SECURITY}/${this.constants.CTX.MASTER_DATA_MANAGEMENT}`, value, {}).then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl[key] = res.data.items;
                }
            });
        });
    }

    public onSelectedChanged(): void {
        const recurrenceStatus = this.clinicalCourseForm.value.recurrenceStatusCode;

        console.log(`I:--START--:--RecurrenceStatus OnSelectedChanged--:recurrenceStatusCode/${recurrenceStatus}`);

        if (recurrenceStatus === '01') {
            this.cssCol = 'col-md-4';
            this.isRecurDateShow = true;
            this.clinicalCourseForm.get("recurrenceDate").setValidators([ValidationService.requiredValidator]);
            this.clinicalCourseForm.get("recurrenceDate").updateValueAndValidity();
        } else {
            this.isRecurDateShow = false;
            this.cssCol = 'col-md-6';

            // Clear the validation
            this.clinicalCourseForm.get("recurrenceDate").clearValidators();
            this.clinicalCourseForm.get("recurrenceDate").updateValueAndValidity();
        }
    }

    private buildForm(): void {
        this.clinicalCourseForm = this.fb.group({
            'recurrenceStatusCode': ['', [
                ValidationService.requiredValidator
            ]
            ],
            'recurrenceDate': [null, []],
            'recordDate': [null, [
                ValidationService.requiredValidator
            ]
            ],
            'status': [this.constants.STATUS_ACTIVE],
            'id': ['']
        });
    }

    public trackByFn(index, item) {
        return index;
    }

    private clearInputError(inputName: string): void {
        this.util.clearInputError(this.clinicalCourseForm, inputName);
    }

    private addInputError(inputName: string, errors: any): void {
        this.util.addInputError(this.clinicalCourseForm, inputName, errors);
    }

    public isDateInvalid(inputName: string) {
        this.clearInputError(inputName);
        const dt = this.clinicalCourseForm.value[inputName];

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
            this.temp[key] = val;
        }
    }

    private clearInputValue(inputName: string): void {
        const control: any = {};
        control[inputName] = null;
        this.clinicalCourseForm.patchValue(control);
    }
}
