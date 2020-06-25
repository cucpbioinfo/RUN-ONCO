import { Component, OnInit, Input, ViewChild } from "@angular/core";
import { DataService } from "../../../services/data.service";
import { DataAnlysService } from "../../../services/data-anlys.service";
import { Constants } from "../../../services/constants";
import { MessageCode } from "../../../services/message-code";
import { VariantAnalysisFormComponent } from "../../../components/omics-analysis/variant-analysis-form/variant-analysis-form.component";
import { AppStateService } from "../../../services/app-state.service";
import { PreloadService } from "../../../services/preload.service";
import { ModalService } from "../../../../shared/services/modal.service";
import { TmbConfigFormModalComponent } from "../../../components/omics-analysis/tmb-config-form-modal/tmb-config-form-modal.component";
import { CurrencyFormatterPipe } from "../../../pipes/currency-formatter.pipe";
import { UtilService } from "../../../services/util.service";
import { DateService } from "../../../services/date.service";
import { TranslateService } from "@ngx-translate/core";

@Component({
    selector: "app-tmb-score",
    templateUrl: "./tmb-score.component.html",
    styleUrls: ["./tmb-score.component.scss"]
})
export class TmbScoreComponent implements OnInit {

    public tab: any;
    public patient: any;
    public criteria: any;
    public tmbConfig: any;
    private modalConfig: any;
    public variantParams: any;
    public tmbScoreResults: any;

    @Input() params: any;
    @ViewChild(VariantAnalysisFormComponent) variantAnalysisForm: VariantAnalysisFormComponent;

    constructor(
        private dataService: DataService,
        private dataAnlysService: DataAnlysService,
        private constants: Constants,
        private msg: MessageCode,
        private appState: AppStateService,
        private preload: PreloadService,
        private modalService: ModalService,
        private currencyFormatter: CurrencyFormatterPipe,
        private util: UtilService,
        private dateService: DateService,
        private translate: TranslateService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.patient = this.params;
        this.appState.patient = this.patient;

        this.tab = {
            data: [
                { value: 1, name: "Variant Call" }
            ],
            step: 1,
            styleClass: "tablist"
        };

        this.tmbConfig = {};
        this.tmbConfig.exomeSize = 37.5;
    }

    public search(criteria: any) {
        this.criteria = undefined;
        this.variantParams = undefined;

        this.preload.show();
        setTimeout(() => {
            this.criteria = criteria;
        }, 1000);
        this.preload.hide();
    }

    public updateVariantParams(criteria: any): void {
        this.variantParams = criteria;
        console.log("O:--SUCCESS--:--Update Params--:variantParams/", this.variantParams, ":exomeSize/", this.tmbConfig.exomeSize);

        this.getVariantCallsBySampleId().then((res: any) => {
            if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                this.calcTmbScore(res.data, this.tmbConfig.exomeSize).then((x: any) => {
                    this.tmbScoreResults = x;
                });
            }
        });
    }

    private getVariantCallsBySampleId(): Promise<{}> {
        const p = new Promise((resolve) => {
            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_VARIANT_CALLS_BY_SAMPLE_ID, this.variantParams)
                .then((res: any) => {
                resolve(res);
            });
        });

        return p;
    }

    private calcTmbScore(variantCalls: any, exomeSize: number): Promise<{}> {
        const req: any = {};
        req.exomeSize = exomeSize;
        req.items = variantCalls;

        const p = new Promise((resolve) => {
            this.dataAnlysService.connect(this.constants.SERVICE_NAME.CALC_TMB_SCORE, req)
                .subscribe((res: any) => {
                    resolve(res);
                });
        });

        return p;
    }

    public trackByFn(index, item) {
        return index;
    }

    public editConfig(): void {
        this.modalConfig = { selected: this.tmbConfig };
        const modalRef = this.modalService.show(TmbConfigFormModalComponent, 'modal-md', this.modalConfig);
        modalRef.subscribe((config: any) => {
            this.tmbConfig = config;
        });
    }

    public downloadCsv(): void {
        const csv: any = [];
        const BOM = "\uFEFF";

        const header = [this.translate.instant('lbl.exomeSize'),
                        this.tmbConfig.exomeSize,
                        this.translate.instant('lbl.tmb'),
                        this.translate.instant('lbl.tmbLevel')];
        csv.push(header);
        this.tmbScoreResults.forEach((tmbScore: any) => {
            const data: any = [];
            data[0] = tmbScore.typeDesc;
            data[1] = this.currencyFormatter.transform(tmbScore.numOfVariant, false, 0);
            data[2] = this.currencyFormatter.transform(tmbScore.rateOfVariant, false);
            data[3] = tmbScore.level ? tmbScore.level : '';
            csv.push(data.join(this.constants.CSV_COLUMN_SEPARATOR));
        });

        csv.push(['', '', '', '']);
        csv.push([this.translate.instant('msg.tmbLevel.title'), '', '', '']);
        csv.push([`- ${this.translate.instant('msg.tmbLevel.low')}`, '', '', '']);
        csv.push([`- ${this.translate.instant('msg.tmbLevel.intermediate')}`, '', '', '']);
        csv.push([`- ${this.translate.instant('msg.tmbLevel.high')}`, '', '', '']);

        const csvArray = csv.join(this.constants.CSV_LINE_SEPARATOR);
        this.util.saveFile(BOM + csvArray, this.fileName, 'text/csv');
    }

    private get fileName(): string {
        const patient = this.appState.patient;

        const sampleVcfList = this.variantAnalysisForm.sampleVcfList;
        const sample = sampleVcfList.find((x: any) => x.id === this.variantParams.sampleVcfIds[0]);
        const biospecimen = sample.biospecimen;

        const currentDate = this.dateService.getCurrentDate();
        const str = this.dateService.formatDate(currentDate, this.constants.FORMAT_TXN_DATE);

        const name = `TMB_${patient.hn}_${biospecimen.ref}_${sample.sequenceType.name}_${str}.csv`;
        return name;
    }
}
