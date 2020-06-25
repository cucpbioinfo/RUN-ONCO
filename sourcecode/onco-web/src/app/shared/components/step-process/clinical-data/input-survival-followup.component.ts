import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from '@angular/core';
import { DataService } from '../../../services/data.service';
import { AppStateService } from '../../../services/app-state.service';
import { MessageCode } from '../../../services/message-code';
import { FormBuilder } from '@angular/forms';
import { Constants } from '../../../services/constants';
import { UtilService } from '../../../services/util.service';
import { ValidationService } from '../../../services/validation.service';

@Component({
    selector: 'app-input-survival-followup',
    templateUrl: './input-survival-followup.component.html'
})
export class InputSurvivalFollowupComponent implements OnInit, AfterViewInit {

    public formValues: any;
    public followUpForm: any;
    public submitted = false;
    public isDeathDateShow = false;

    public get survivalFollowUp() {
        const data: any = {};
        data.lastFollowUpDate = this.followUpForm.value.lastFollowUpDate;
        data.deathDate = this.followUpForm.value.deathDate;
        data.recordDate = this.followUpForm.value.recordDate;

        if (!this.util.isNullOrEmpty(this.followUpForm.value.vitalStatusCode)) {
            data.vitalStatus = {};
            data.vitalStatus.code = this.followUpForm.value.vitalStatusCode;
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

    constructor(private dataService: DataService,
        private appState: AppStateService,
        private msg: MessageCode,
        private fb: FormBuilder,
        public constants: Constants,
        private cd: ChangeDetectorRef,
        private util: UtilService) {
    }

    ngOnInit(): void {
        this.init();
    }

    ngAfterViewInit(): void {
        if (this.formValues) {
            const values = {
                vitalStatusCode: this.formValues.vitalStatus ? this.formValues.vitalStatus.code : '',
                lastFollowUpDate: this.formValues.lastFollowUpDate,
                deathDate: this.formValues.deathDate,
                recordDate: this.formValues.recordDate
            };

            this.followUpForm.patchValue(values);
        }

        this.cd.detectChanges();
    }

    private init(): void {
        console.log('I:--START--:--OnLoad InputSurvivalFollowupComponent--');

        this.buildForm();

        if (this.appState.clinicalData.survivalFollowUp) {
            this.formValues = this.appState.clinicalData.survivalFollowUp;
        }

        this.dataMapper.forEach((value: string, key: string) => {
            this.dataService.connect(`${this.constants.CTX.SECURITY}/${this.constants.CTX.MASTER_DATA_MANAGEMENT}`, value, {}).then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl[key] = res.data.items;
                }
            });
        });
    }

    public onSelectedChanged(): void {
        const vitalStatusCode = this.followUpForm.value.vitalStatusCode;

        console.log(`I:--START--:--VitalStatus OnSelectedChanged--:vitalStatusCode/${vitalStatusCode}`);

        if (vitalStatusCode === '02') {
            this.isDeathDateShow = true;
            this.followUpForm.get("deathDate").setValidators([ValidationService.requiredValidator]);
            this.followUpForm.get("deathDate").updateValueAndValidity();
        } else {
            this.isDeathDateShow = false;

            // Clear the validation
            this.followUpForm.get("deathDate").clearValidators();
            this.followUpForm.get("deathDate").updateValueAndValidity();
        }
    }

    private buildForm(): void {
        this.followUpForm = this.fb.group({
            'vitalStatusCode': ['', [
                ValidationService.requiredValidator
            ]
            ],
            'lastFollowUpDate': [null, [
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
            ]
        });
    }

    public trackByFn(index, item) {
        return index;
    }
}
