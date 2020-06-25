import { AppStateService } from '../services/app-state.service';
import { UtilService } from '../services/util.service';
import { Constants } from '../services/constants';
import { Injectable } from '@angular/core';

@Injectable()
export class ValidationService {

    constructor(private appState: AppStateService,
        private util: UtilService,
        private constants: Constants
    ) {
    }

    static getValidatorErrorMessage(validatorName: string, validatorValue?: any) {
        const config = {
            'required': 'valErr.required',
            'duplicated': 'valErr.duplicated',
            'invalidHeight': 'valErr.invalidHeight',
            'invalidWeight': 'valErr.invalidWeight',
            'invalidDate': 'valErr.invalidDate',
            'invalidInitWeight': 'valErr.invalidInitWeight',
            'invalidClusterNo': 'valErr.invalidClusterNo',
            'invalidEmail': 'valErr.invalidEmail'
        };

        return config[validatorName];
    }

    static requiredValidator(control) {
        const input = control.value;

        if (input === undefined || input == null || input === '' || input.length === 0) {
            return { 'required': true };
        }

        return null;
    }

    static nonStringValidator(control) {
        if (control.value.match(/^\d+$/)) {
            return null;
        } else {
            return { 'invalidNonString': true };
        }
    }

    static heightValidator(control) {
        if (control.value.match(/^\d{1}(\.\d{1,2})?$/)) {
            return null;
        }

        return { 'invalidHeight': true };
    }

    static weightValidator(control) {
        if (control.value.match(/^\d{0,3}(\.\d{1,2})?$/)) {
            return null;
        }

        return { 'invalidWeight': true };
    }

    static initWeightValidator(control) {
        if (control.value.match(/^(\d{1})?(,\d{1,3})?(\d{1,3})*(\.\d{1,2})?$/)) {
            return null;
        }

        return { 'invalidInitWeight': true };
    }

    static clusterNoValidator(control) {
        // if (control.value.match(/^\d{1,2}$/)) {
        if (control.value.match(/^[1-7]{1}$/)) {
            return null;
        }

        return { 'invalidClusterNo': true };
    }

    static emailValidator(control) {
        const re = /^(([^<>()[\]\\.,;:\s@\"]+(\.[^<>()[\]\\.,;:\s@\"]+)*)|(\".+\"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
        if (control.value.match(re)) {
            return null;
        }

        return { 'invalidEmail': true };
    }

    public validateClinicalData(): boolean {
        let valid = true;
        const data = this.appState.clinicalData;
        console.log(data);

        // Validate patient
        if (data.patient) {
            const patientForm = this.util.validateForm(data.patient, this.constants.VALIDATE.SAVE_CLINICAL_DATA.PATIENT);
            console.log('---> Validate Patient: ', patientForm);

            if (!patientForm.valid) {
                valid = false;
            }
        }

        // Validate diagnosisList
        if (data.diagnosisList && data.diagnosisList.length > 0) {
            let keepGoing = true;

            data.diagnosisList.forEach((item, idx) => {
                if (keepGoing) {
                    console.log('---> item : ', item);
                    const diagnosisForm = this.util.validateForm(item, this.constants.VALIDATE.SAVE_CLINICAL_DATA.DIAGNOSIS_LIST);
                    console.log('---> diagnosisForm : ', diagnosisForm);

                    if (!diagnosisForm.valid) {
                        valid = false;
                        keepGoing = false;
                    }
                }
            });
        } else {
            valid = false;
        }

        // Validate pathological
        if (data.pathological) {
            const pathologicalForm = this.util.validateForm(data.pathological, this.constants.VALIDATE.SAVE_CLINICAL_DATA.PATHOLOGICAL);
            console.log('---> Validate Pathological: ', pathologicalForm);

            if (!pathologicalForm.valid) {
                valid = false;
            }
        } else {
            valid = false;
        }

        // Validate cancerStage
        if (data.cancerStage) {
            const validCancerStage = this.constants.VALIDATE.SAVE_CLINICAL_DATA.CANCER_STAGE;

            if (data.cancerStage.stageType) {
                const stageTypeCode = data.cancerStage.stageType.code;

                if (stageTypeCode === '01') {
                    validCancerStage.push('cTStage');
                    validCancerStage.push('cNStage');
                } else if (stageTypeCode === '02') {
                    validCancerStage.push('pTStage');
                    validCancerStage.push('pNStage');
                } else if (stageTypeCode === '03') {
                    validCancerStage.push('ypTStage');
                    validCancerStage.push('ypNStage');
                }
            }

            console.log('--->  data.cancerStage: ', data.cancerStage, validCancerStage);
            const cancerStageForm = this.util.validateForm(data.cancerStage, validCancerStage);
            console.log('---> Validate CancerStage: ', cancerStageForm);

            if (!cancerStageForm.valid) {
                valid = false;
            }
        } else {
            valid = false;
        }

        // Validate clinicalCourse
        if (data.clinicalCourse) {
            console.log('--->  data.clinicalCourse: ', data.clinicalCourse);
            // tslint:disable-next-line:max-line-length
            const clinicalCourseForm = this.util.validateForm(data.clinicalCourse, this.constants.VALIDATE.SAVE_CLINICAL_DATA.CLINICAL_COURSE);
            console.log('---> Validate ClinicalCourse: ', clinicalCourseForm);

            if (!clinicalCourseForm.valid) {
                valid = false;
            }
        } else {
            valid = false;
        }

        // Validate survivalFollowUp
        if (data.survivalFollowUp) {
            console.log('--->  data.survivalFollowUp: ', data.survivalFollowUp);
            const survivalFollowUpForm = this.util.validateForm(data.survivalFollowUp, this.constants.VALIDATE.SAVE_CLINICAL_DATA.SURVIVAL_FOLLOW_UP);
            console.log('---> Validate SurvivalFollowup: ', survivalFollowUpForm);

            if (!survivalFollowUpForm.valid) {
                valid = false;
            }
        } else {
            valid = false;
        }

        return valid;
    }
}
