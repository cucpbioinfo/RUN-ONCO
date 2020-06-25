import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { ValidationService } from "../../../../shared/services/validation.service";
import { DataService } from "../../../../shared/services/data.service";
import { Constants } from "../../../../shared/services/constants";
import { MessageCode } from "../../../../shared/services/message-code";

declare var $: any;

@Component({
    selector: "app-variant-analysis-form",
    templateUrl: "./variant-analysis-form.component.html",
    styleUrls: ["./variant-analysis-form.component.scss"]
})
export class VariantAnalysisFormComponent implements OnInit {

    public variantForm: any;
    public submitted: boolean;
    public sampleVcfList: any;
    public initialize = false;

    @Input() public criteria: any;
    @Input() public isMultiSelect = true;
    @Output() updatedChange = new EventEmitter();

    constructor(
        private fb: FormBuilder,
        private dataService: DataService,
        private constants: Constants,
        private msg: MessageCode
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.submitted = false;
        this.buildForm();
        this.getSampleVcfList();
    }

    private buildForm(): void {
        this.variantForm = this.fb.group({
            sampleVcfIds: ["", [ValidationService.requiredValidator]]
        });
    }

    private initMultiSelect(): void {
        $(function() { $('.selectpicker').selectpicker(); });
    }

    public trackByFn(index, item) {
        return index;
    }

    public update() {
        this.submitted = true;
        const criteria = JSON.parse(JSON.stringify(this.criteria));
        console.log(`I:--START--:--Update Params--:criteria/`, criteria);

        if (this.variantForm.valid) {
            const values = this.variantForm.value.sampleVcfIds;
            if (Array.isArray(values)) {
                criteria.sampleVcfIds = values;
            } else {
                criteria.sampleVcfIds = [values];
            }

            console.log('O:--SUCCESS--:--Update Params--:variantParams/', criteria);
            this.updatedChange.emit(criteria);
        }
    }

    private getSampleVcfList() {
        this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_SAMPLE_VCF_LIST_BY_BIOSPECIMENS, this.criteria)
            .then((res: any) => {
            console.log('--->  res : ', res);
            if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                this.sampleVcfList = res.data;
                this.initMultiSelect();
                this.initialize = true;
            }
        });
    }

    public getSampleName(sample: any) {
        const biospecimen = sample.biospecimen;
        return `${biospecimen.ref}_${sample.sequenceType.name}`;
    }
}
