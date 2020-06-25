import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { DataService } from '../../../../services/data.service';
import { MessageCode } from '../../../../services/message-code';
import { FormBuilder } from '@angular/forms';
import { Constants } from '../../../../services/constants';
import { UtilService } from '../../../../services/util.service';
import { ValidationService } from '../../../../services/validation.service';
import { Subject } from "rxjs";
import { BsModalRef } from "ngx-bootstrap/modal";
import { DateService } from "../../../../services/date.service";
import { Dictionary } from '../../../../bean/dictionary';

@Component({
    selector: "app-survival-followup-form-modal",
    templateUrl: "./survival-followup-form-modal.component.html",
    styleUrls: ["./survival-followup-form-modal.component.scss"]
})
export class SurvivalFollowupFormModalComponent implements OnInit, AfterViewInit {

    public followupForm: any;
    public submitted = false;
    public isDeathDateShow = false;
    public onClose: Subject<any>;
    public selected: any;
    public cssCol: string;
    public minDate: any;
    public dt: Date;
    public temp: Dictionary = {};

    public get survivalFollowup() {
        const data: any = {};
        data.id = this.followupForm.value.id;
        data.lastFollowupDate = this.temp['lastFollowupDate']; // this.followupForm.value.lastFollowupDate;
        data.deathDate = this.temp['deathDate']; // this.followupForm.value.deathDate;
        data.recordDate = this.temp['recordDate']; // this.followupForm.value.recordDate;
        data.status = this.followupForm.value.status;

        if (!this.util.isNullOrEmpty(this.followupForm.value.vitalStatusCode)) {
            data.vitalStatus = this.ddl.vitalStatusList.find((x: any) => x.code === this.followupForm.value.vitalStatusCode);
        }

        return data;
    }

    public ddl: any = {
        vitalStatusList: []
    };

    public dataMapper: Map<string, string> = new Map<string, string>(
        [
            ['vitalStatusList', this.constants.SERVICE_NAME.GET_VITAL_STATUS_LIST]
        ]
    );

    constructor(private masterDataService: DataService,
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

    public onSave() {
        this.submitted = true;

        if (this.followupForm.valid) {
            this.onClose.next(this.survivalFollowup);
            this.bsModalRef.hide();
        }
    }

    ngAfterViewInit(): void {
        if (this.selected) {
            const values: any = {};
            values.id = this.selected.id;
            // values.lastFollowupDate = this.selected.lastFollowupDate;
            // values.deathDate = this.selected.deathDate;
            // values.recordDate = this.selected.recordDate;
            values.status = this.selected.status;

            if (this.selected.lastFollowupDate) {
                values.lastFollowupDate = new Date(this.selected.lastFollowupDate);
            }
            if (this.selected.deathDate) {
                values.deathDate = new Date(this.selected.deathDate);
            }
            if (this.selected.recordDate) {
                values.recordDate = new Date(this.selected.recordDate);
            }
            if (this.selected.vitalStatus) {
                values.vitalStatusCode = this.selected.vitalStatus.code;
            }

            this.followupForm.patchValue(values);
            this.onSelectedChanged();
        }

        this.cd.detectChanges();
    }

    private init(): void {
        console.log('I:--START--:--OnLoad InputSurvivalFollowupComponent--');
        this.submitted = false;
        this.buildForm();
        this.cssCol = 'col-md-4';

        this.dt = this.dateService.getCurrentDate();
        this.minDate = this.dateService.parseDate('1900-01-01');

        this.dataMapper.forEach((value: string, key: string) => {
            this.masterDataService.connect(`${this.constants.CTX.SECURITY}/${this.constants.CTX.MASTER_DATA_MANAGEMENT}`, value, {}).then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl[key] = res.data.items;
                }
            });
        });
    }

    public onSelectedChanged(): void {
        const vitalStatusCode = this.followupForm.value.vitalStatusCode;

        console.log(`I:--START--:--VitalStatus OnSelectedChanged--:vitalStatusCode/${vitalStatusCode}`);

        if (vitalStatusCode === '02') {
            this.isDeathDateShow = true;
            this.followupForm.get("deathDate").setValidators([ValidationService.requiredValidator]);
            this.followupForm.get("deathDate").updateValueAndValidity();

            this.cssCol = 'col-md-3';
        } else {
            this.isDeathDateShow = false;
            this.followupForm.patchValue({deathDate : null});
            this.cssCol = 'col-md-4';

            // Clear the validation
            this.followupForm.get("deathDate").clearValidators();
            this.followupForm.get("deathDate").updateValueAndValidity();
        }
    }

    private buildForm(): void {
        this.followupForm = this.fb.group({
            'id': [''],
            'vitalStatusCode': ['', [
                ValidationService.requiredValidator
            ]
            ],
            'lastFollowupDate': [null, [
                ValidationService.requiredValidator
            ]
            ],
            'deathDate': [null, [
                // ValidationService.requiredValidator
            ]
            ],
            'recordDate': [null, [
                ValidationService.requiredValidator
            ]
            ],
            'status': [this.constants.STATUS_ACTIVE]
        });
    }

    public trackByFn(index, item) {
        return index;
    }

    private clearInputError(inputName: string): void {
        this.util.clearInputError(this.followupForm, inputName);
    }

    private addInputError(inputName: string, errors: any): void {
        this.util.addInputError(this.followupForm, inputName, errors);
    }

    public isDateInvalid(inputName: string) {
        this.clearInputError(inputName);
        const dt = this.followupForm.value[inputName];

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
        this.followupForm.patchValue(control);
    }
}
