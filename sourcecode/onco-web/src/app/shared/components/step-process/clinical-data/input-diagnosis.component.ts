import { Component, OnInit, AfterViewInit, ViewChild, ChangeDetectorRef } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { Constants } from "../../../services/constants";
import { ValidationService } from "../../../services/validation.service";
import { DataService } from "../../../services/data.service";
import { Observable } from "rxjs";
import { TypeaheadMatch } from "ngx-bootstrap/typeahead";
import { MessageCode } from "../../../services/message-code";
import { Router } from '@angular/router';
import { InputCancerStageComponent } from "../../../../shared/components/step-process/clinical-data/input-cancer-stage.component";
import { AppStateService } from "../../../services/app-state.service";
import { UtilService } from "../../../services/util.service";
import { Dictionary } from '../../../bean/dictionary';

@Component({
    selector: 'app-input-diagnosis',
    templateUrl: './input-diagnosis.component.html'
})
export class InputDiagnosisComponent implements OnInit, AfterViewInit {

    public submitted: boolean;
    public diagnosisForm: any;
    public tab: any;
    public prevTab: any;
    public formValues: any;
    public temp: Dictionary = {};

    typeaheadLoading: boolean;
    noResult = true;
    dataSource: Observable<any>;
    diagnosisList: any;

    @ViewChild(InputCancerStageComponent)
    cancerStageComponent: InputCancerStageComponent;

    constructor(
        public fb: FormBuilder,
        public constants: Constants,
        public dataService: DataService,
        public msg: MessageCode,
        public router: Router,
        public appState: AppStateService,
        public util: UtilService,
        private cd: ChangeDetectorRef
    ) {
        this.dataSource = Observable.create((observer: any) => {
            const req = {
                icd: {
                    name: this.diagnosisForm.value.primaryDiagnosisDesc,
                    // version: 'ICD-10:2016',
                    isDefault: this.constants.YES,
                    status: this.constants.STATUS_ACTIVE
                },
                paging: { startIndex: 0, fetchSize: 10 }
            };

            this.dataService.filter(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.FILTER_ICD, req, true).subscribe((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.diagnosisList = res.data.items;

                    const names = [];
                    res.data.items.forEach((x: any) => {
                        names.push(x.name);
                    });
                    observer.next(names);
                } else {
                    observer.next();
                }
            });
        });
    }

    ngOnInit(): void {
        this.init();
    }

    ngAfterViewInit(): void {

        if (this.formValues) {
            const values: any = {};
            values.status = this.formValues.status;

            if (!this.util.isNullOrEmpty(this.formValues.diagnosisDate)) {
                values.diagnosisDate = new Date(this.formValues.diagnosisDate);
            }
            if (this.formValues.primaryDiagnosis) {
                values.primaryDiagnosisCode = this.formValues.primaryDiagnosis.code;
                values.primaryDiagnosisDesc = this.formValues.primaryDiagnosis.name;
            }

            this.diagnosisForm.patchValue(values);
        }

        this.cd.detectChanges();
    }

    public changeTypeaheadLoading(e: boolean): void {
        this.typeaheadLoading = e;
    }

    public typeaheadOnSelect(e: TypeaheadMatch): void {
        const selected = this.diagnosisList.find((x: any) => x.name === e.value);

        if (selected) {
            this.noResult = false;
            this.diagnosisForm.patchValue({primaryDiagnosisCode: selected.code});
        }
    }

    public typeaheadNoResults(event: boolean): void {
        this.noResult = event;
        if (this.noResult) {
            this.diagnosisForm.patchValue({primaryDiagnosisCode: ''});
        }
    }

    public onTextChanged(): void {
        if (!this.util.isEmptyObject(this.diagnosisForm.value.primaryDiagnosisDesc)) {
            this.diagnosisForm.patchValue({primaryDiagnosisCode: ''});
        }
    }

    public onSave() {
        this.submitted = true;

        if (this.diagnosisForm.valid) {
            this.save();
            this.router.navigate(['clinical-record-form/step1']);
        }
    }

    public onSaveAndNext() {
        this.submitted = true;

        if (this.diagnosisForm.valid) {
            this.save();
            this.tab.step += 1;
            const step = this.tab.data.find((x: any) => x.value === this.tab.step);
            this.goToNextTab(step);
        }
    }

    private save(): void {
        const diagnosis = this.appState.currentDx;
        diagnosis.diagnosisDate =  this.temp['diagnosisDate'];

        diagnosis.primaryDiagnosis = {};
        diagnosis.primaryDiagnosis.code = this.diagnosisForm.value.primaryDiagnosisCode;
        diagnosis.primaryDiagnosis.name = this.diagnosisForm.value.primaryDiagnosisDesc;
        diagnosis.status = this.constants.STATUS_ACTIVE;

        if (this.cancerStageComponent) {
            const cancerStage = this.cancerStageComponent.cancerStage;
            this.appState.currentDx.cancerStage = cancerStage;
        }
    }

    public onCancel(): void {
        this.router.navigate(['clinical-record-form/step1']);
    }

    public goToNextTab(step: any) {
        this.submitted = false;
        this.prevTab = step.value;
        this.tab.step = step.value;
    }

    private init(): void {
        this.submitted = false;
        this.buildForm();
        this.util.removeClass('.footer-actions', 'ext-footer-actions');

        this.tab = {
            data: [
                { value: 1, name: 'lbl.pathological' },
                { value: 2, name: 'lbl.cancerStage' },
                { value: 3, name: 'lbl.clinicalCourse' }
            ],
            step: 1,
            styleClass: 'tablist'
        };

        this.prevTab = this.tab.step;

        if (this.appState.currentDx) {
            this.formValues = this.appState.currentDx;
        }
    }

    public bsValueChange(key: string, val: Date) {
        if (val) {
            this.temp[key] = new Date(val.getTime() - val.getTimezoneOffset() * 60 * 1000);
        } else {
            this.temp[key] = val;
        }
    }

    private buildForm(): void {
        this.diagnosisForm = this.fb.group({
            diagnosisDate: [null, [ValidationService.requiredValidator]],
            primaryDiagnosisCode: ['', [ValidationService.requiredValidator]],
            primaryDiagnosisDesc: ['', [ValidationService.requiredValidator]],
            status: ['']
        });
    }
}
