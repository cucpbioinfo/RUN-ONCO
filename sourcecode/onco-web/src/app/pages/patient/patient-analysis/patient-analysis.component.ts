import { Component, OnInit, ViewChild } from "@angular/core";
import { DataService } from "../../../shared/services/data.service";
import { Constants } from "../../../shared/services/constants";
import { MessageCode } from "../../../shared/services/message-code";
import { ModalService } from "../../../shared/services/modal.service";
import { AlertDialogComponent } from "../../../shared/components/modal-dialog/alert-dialog/alert-dialog.component";
import { AppStateService } from "../../../shared/services/app-state.service";
import { DataAnlysService } from "../../../shared/services/data-anlys.service";
import { VariantAnalysisFormComponent } from "../../../shared/components/omics-analysis/variant-analysis-form/variant-analysis-form.component";
import { PreloadService } from "../../../shared/services/preload.service";

@Component({
  selector: 'app-patient-analysis',
  templateUrl: './patient-analysis.component.html',
  styleUrls: ['./patient-analysis.component.scss']
})
export class PatientAnalysisComponent implements OnInit {

    private modalConfig: any;
    public biospecimenList: any;
    public tab: any;
    public prevTab: any;
    public patient: any;
    public criteria: any;
    public variantForm: any;
    public variantParams: any;
    public rnaSeqParams: any;
    public rnaSeqForm: any;
    public breadcrumbs: any;
    public clustergrammer: any;
    public ideogram: any;
    public genes: any;

    @ViewChild(VariantAnalysisFormComponent) variantAnalysisForm: VariantAnalysisFormComponent;

    constructor(
        private dataService: DataService,
        private constants: Constants,
        private msg: MessageCode,
        private modalService: ModalService,
        private appState: AppStateService,
        private dataAnlysService: DataAnlysService,
        private preload: PreloadService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.patient = this.appState.patient;

        this.tab = {
            data: [
                { value: 1, name: "Variant Call" },
                { value: 2, name: "RNA-Seq" }
            ],
            step: 1,
            styleClass: "tablist"
        };

        this.breadcrumbs = [
            {
                name: 'Search',
                url: '/patient-search',
                active: false
            },
            {
                name: 'Patient Analysis',
                url: '/patient-analysis',
                active: true
            }
        ];
    }

    private getOmicsAnalysis() {
        this.clearData();

        // this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.INDIV_CLUSTERGRAMMER, this.rnaSeqParams).then((res: any) => {
        //     console.log('I:--START--:--Get Individual Clustergrammer--:res/', res);

        //     if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
        //         this.clustergrammer = res.data.clustergrammer;
        //         this.ideogram = res.data.ideogram.container;
        //         this.genes = res.data.ideogram.datatable;
        //     } else {
        //         this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
        //         this.modalService.show(AlertDialogComponent, 'model-sm', this.modalConfig);
        //     }
        // });

        this.getRnaSeqListForClustergrammer().then((res: any) => {
            console.log('--->  res : ', res);
            if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                const sampleOmics = res.data;
                this.prepareDataForClustergrammer(sampleOmics);
            } else {
                this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                this.modalService.show(AlertDialogComponent, 'model-sm', this.modalConfig);
            }
        });
    }

    public goToNextTab(step: any) {
        this.prevTab = step.value;
        this.tab.step = step.value;
    }

    public trackByFn(index, item) {
        return index;
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
        console.log('O:--SUCCESS--:--Update Params--:variantParams/', this.variantParams);
    }

    public updateRnaSeqParams(criteria: any): void {
        this.rnaSeqParams = criteria;
        console.log('O:--SUCCESS--:--Update Params--:rnaSeqParams/', this.rnaSeqParams);
        this.getOmicsAnalysis();
    }

    private clearData(): void {
        delete this.clustergrammer;
        delete this.ideogram;
        delete this.genes;
    }

    private getRnaSeqListForClustergrammer(): Promise<{}> {
        const criteria = JSON.parse(JSON.stringify(this.rnaSeqParams));
        delete criteria.clusterConfig;

        const p = new Promise((resolve) => {
            this.dataService
            .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_RNA_SEQ_LIST_FOR_CLUSTERGRAMMER, this.rnaSeqParams)
            .then((res: any) => {
                resolve(res);
            });
        });

        return p;
    }

    private prepareDataForClustergrammer(sampleOmics) {
        const req: any = {};
        req.sampleOmics = sampleOmics;
        req.sampleCluster = this.sampleCluster;
        req.geneCluster = this.geneCluster;

        console.log('---> req : ', req);

        this.dataAnlysService.connect('clustergrammer', req).subscribe((res: any) => {
            console.log('--->  res : ', res);
            this.clustergrammer = res.clustergrammer;
            this.ideogram = res.ideogram.container;
            this.genes = res.ideogram.datatable;
        });

    }

    private get defaultConfig(): any {
        const config: any = {};
        config.init = "k-means++";
        config.random_state = "10";
        config.algorithm = "auto";
        if (this.clusterConfig.code === 'agglomerative') {
            config.affinity = "euclidean";
            config.linkage = "ward";
        }
        return config;
    }

    private get sampleCluster() {
        const params: any = {};
        params.config = this.defaultConfig;
        if (this.clusterConfig) {
            params.algorithm = this.clusterConfig.clusterAlgo.code;
            params.config.n_clusters = "1";
        }

        return params;
    }

    private get geneCluster() {
        const params: any = {};
        params.config = this.defaultConfig;
        if (this.clusterConfig) {
            params.algorithm = this.clusterConfig.clusterAlgo.code;
            params.config.n_clusters = this.clusterConfig.nCluster;
        }

        return params;
    }

    private get clusterConfig(): any {
        return this.rnaSeqParams.clusterConfig;
    }

    public get sampleVcfList() {
        if (this.variantAnalysisForm) {
            return this.variantAnalysisForm.sampleVcfList;
        }
    }

    public get initialize() {
        if (this.variantAnalysisForm) {
            return this.variantAnalysisForm.initialize;
        }

        return false;
    }
}
