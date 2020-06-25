import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { Constants } from "../../../services/constants";
import { MessageCode } from "../../../services/message-code";
import { AppStateService } from "../../../services/app-state.service";
import { FormBuilder } from "@angular/forms";
import { DataService } from "../../../services/data.service";
import { UtilService } from "../../../services/util.service";
import { ValidationService } from "../../../services/validation.service";

declare var $: any;

@Component({
    selector: "app-omics-analysis-form",
    templateUrl: "./omics-analysis-form.component.html",
    styleUrls: ["./omics-analysis-form.component.scss"]
})
export class OmicsAnalysisFormComponent implements OnInit {

    public analysisForm: any;
    public selected: any = [];
    public submitted: boolean;

    public ddl: any = {
        sampleSourceList: [],
        seqTypeList: []
    };

    @Input() public title = "";
    @Input() public patient: any;

    @Output() searchChange = new EventEmitter();

    constructor(
        private constants: Constants,
        private msg: MessageCode,
        private appState: AppStateService,
        private fb: FormBuilder,
        private dataService: DataService,
        private util: UtilService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.buildForm();

        const p1 = this.getSeqTypeList();
        const p2 = this.getSampleSourceList();

        Promise.all([p1, p2]).then((values) => {
            values.forEach((v) => { console.log(v); });
        }).catch(err => {
            console.log(err);
        }).finally(() => {
            this.initMultiSelect();
        });
    }

    private buildForm(): void {
        this.analysisForm = this.fb.group({
            sampleSource: ["", [ValidationService.requiredValidator]],
            seqType: ["", [ValidationService.requiredValidator]]
        });
    }

    public trackByFn(index, item) {
        return index;
    }

    public get criteria() {
        const req: any = {};

        if (this.patient) {
            req.patient = {};
            req.patient.id = this.patient.id;
        }

        const sampleSource = this.analysisForm.value.sampleSource;
        if (sampleSource && sampleSource.length > 0) {
            req.biospecimenList = [];

            sampleSource.forEach((biospecimenId: any) => {
                req.biospecimenList.push({ id: biospecimenId });
            });
        }

        if (!this.util.isNullOrEmpty(this.analysisForm.value.seqType)) {
            req.sequenceType = {};
            req.sequenceType.code = this.analysisForm.value.seqType;
        }

        return req;
    }

    public search() {
        this.submitted = true;
        if (this.analysisForm.valid) {
            this.searchChange.emit(this.criteria);
        }
    }

    private initMultiSelect(): void {
        $(function() { $('.selectpicker').selectpicker(); });
    }

    private getSeqTypeList(): Promise<{}> {
        const p = new Promise((resolve) => {
            this.dataService.connect(`${this.constants.CTX.SECURITY}/${this.constants.CTX.MASTER_DATA_MANAGEMENT}`, this.constants.SERVICE_NAME.GET_SEQ_TYPE_LIST, {}).then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl.seqTypeList = res.data.items;
                }

                resolve(`service.getSeqTypeList() has been resolved.`);
            });
        });

        return p;
    }

    private getSampleSourceList(): Promise<{}> {
        const p = new Promise((resolve) => {
            this.dataService
            .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_SAMPLE_SOURCE_LIST, this.appState.patient)
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl.sampleSourceList = res.data.items;
                }

                resolve(`service.getSampleSourceList() has been resolved.`);
            });
        });

        return p;
    }
}
