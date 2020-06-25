import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from "@angular/core";
import { DataService } from "../../../services/data.service";
import { AppStateService } from "../../../services/app-state.service";
import { MessageCode } from "../../../services/message-code";
import { ValidationService } from "../../../services/validation.service";
import { FormBuilder } from "@angular/forms";
import { Constants } from "../../../services/constants";
import { UtilService } from "../../../services/util.service";

@Component({
    selector: "app-input-cancer-stage",
    templateUrl: "./input-cancer-stage.component.html"
})
export class InputCancerStageComponent implements OnInit, AfterViewInit {

    public formValues: any;
    public submitted = false;
    public cancerStageForm: any;
    public isCTStageShow: boolean;
    public isPTStageShow: boolean;
    public isYpStageShow: boolean;
    private allStageType = ["cT", "cN", "pT", "pN", "ypT", "ypN"];
    private initialized = true;

    private fields: any = [
        'id',
        'ajcc',
        'stageType',
        'cTStage',
        'cTSubstage',
        'cNStage',
        'cNSubstage',
        'pTStage',
        'pTSubstage',
        'pNStage',
        'pNSubstage',
        'ypTStage',
        'ypTSubstage',
        'ypNStage',
        'ypNSubstage',
        'mStage',
        'mSubstage',
        'clinicalStage',
        'pathoStage',
        'pathoSubstage'
    ];

    public get cancerStage() {
        const data: any = {};

        this.fields.forEach((x: any) => {
            this.util.bindData(this.cancerStageForm, data, x);
        });

        return data;
    }

    public ddl: any = {
        tissueTypeList: [],
        tissueProcedureList: [],
        histologicGradeList: [],
        substageList: []
    };

    public dataMapper: Map<string, string> = new Map<string, string>([
        ["ajccList", this.constants.SERVICE_NAME.GET_AJCC_LIST],
        ["stageTypeList", this.constants.SERVICE_NAME.GET_STAGE_TYPE_LIST],
        ["cTStageList", this.constants.SERVICE_NAME.GET_CT_STAGE_LIST],
        ["cNStageList", this.constants.SERVICE_NAME.GET_CN_STAGE_LIST],
        ["pTStageList", this.constants.SERVICE_NAME.GET_PT_STAGE_LIST],
        ["pNStageList", this.constants.SERVICE_NAME.GET_PN_STAGE_LIST],
        ["ypTStageList", this.constants.SERVICE_NAME.GET_YPT_STAGE_LIST],
        ["ypNStageList", this.constants.SERVICE_NAME.GET_YPN_STAGE_LIST],
        ["mStageList", this.constants.SERVICE_NAME.GET_M_STAGE_LIST],
        ["clinicalStageList", this.constants.SERVICE_NAME.GET_CLINICAL_STAGE_LIST],
        ["pathoStageList", this.constants.SERVICE_NAME.GET_PATHOLOGICAL_STAGE_LIST],
        ["substageList", this.constants.SERVICE_NAME.GET_CANCER_SUBSTAGE_LIST]
    ]);

    constructor(
        private dataService: DataService,
        private appState: AppStateService,
        private msg: MessageCode,
        private fb: FormBuilder,
        private constants: Constants,
        private cd: ChangeDetectorRef,
        private util: UtilService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    ngAfterViewInit(): void {

        if (this.formValues) {
            const values: any = {};

            this.fields.forEach((x: string) => {
                this.util.bindForm(this.formValues, values, x);
            });

            this.cancerStageForm.patchValue(values);
            this.onSelectedChanged();
            this.initialized = false;
        }

        this.cd.detectChanges();
    }

    private init(): void {
        console.log("I:--START--:--OnLoad InputCancerStageComponent--");

        this.buildForm();
        this.util.addClass('.footer-actions', 'ext-footer-actions');

        this.isCTStageShow = false;
        this.isPTStageShow = false;
        this.isYpStageShow = false;

        if (this.appState.currentDx) {
            if (this.appState.currentDx.cancerStage) {
                this.formValues = this.appState.currentDx.cancerStage;
            }
        }

        this.dataMapper.forEach((value: string, key: string) => {
            this.dataService.connect(`${this.constants.CTX.SECURITY}/${this.constants.CTX.MASTER_DATA_MANAGEMENT}`, value, {}).then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl[key] = res.data.items;
                }
            });
        });
    }

    private buildForm(): void {
        this.cancerStageForm = this.fb.group({
            id: [""],
            ajcc: ["", [ValidationService.requiredValidator]],
            stageType: ["", [ValidationService.requiredValidator]],
            cTStage: ["", []],
            cTSubstage: ["", []],
            cNStage: ["", []],
            cNSubstage: ["", []],
            pTStage: ["", []],
            pTSubstage: ["", []],
            pNStage: ["", []],
            pNSubstage: ["", []],
            ypTStage: ["", []],
            ypTSubstage: ["", []],
            ypNStage: ["", []],
            ypNSubstage: ["", []],
            mStage: ["", [ValidationService.requiredValidator]],
            mSubstage: ["", []],
            clinicalStage: ["", []],
            pathoStage: ["", [ValidationService.requiredValidator]],
            pathoSubstage: ["", []]
        });
    }

    public onSelectedChanged(): void {
        let selected = [];

        // Reset the values
        this.isCTStageShow = false;
        this.isPTStageShow = false;
        this.isYpStageShow = false;

        // Clear the validation
        this.allStageType.forEach(x => {
            this.clearValidatorsAndValue(`${x}Stage`);
            this.clearValidatorsAndValue(`${x}Substage`);
        });

        const stageTypeCode = this.cancerStageForm.value.stageType;

        if (stageTypeCode === "01") {
            this.isCTStageShow = true;
            selected = ["cT", "cN"];
        } else if (stageTypeCode === "02") {
            this.isPTStageShow = true;
            selected = ["pT", "pN"];
        } else if (stageTypeCode === "03") {
            this.isYpStageShow = true;
            selected = ["ypT", "ypN"];
        }

        selected.forEach(x => {
            this.clearValidatorsAndValue(`${x}Stage`);
            this.clearValidatorsAndValue(`${x}Substage`);
        });
    }

    public trackByFn(index, item) {
        return index;
    }

    private clearValidatorsAndValue(inputName: string): void {
        if (!this.initialized) {
            this.cancerStageForm.get(inputName).patchValue('');
        }
        this.cancerStageForm.get(inputName).setValidators([ValidationService.requiredValidator]);
        this.cancerStageForm.get(inputName).updateValueAndValidity();
    }
}
