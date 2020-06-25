import { Component, OnInit, Input } from "@angular/core";
import { DataService } from "../../../services/data.service";
import { Constants } from "../../../services/constants";
import { MessageCode } from "../../../services/message-code";
import { ModalService } from "../../../services/modal.service";
import { AlertDialogComponent } from "../../modal-dialog/alert-dialog/alert-dialog.component";
import { AppStateService } from "../../../services/app-state.service";
import { DataAnlysService } from "../../../services/data-anlys.service";

@Component({
    selector: "app-patient-clustergrammer",
    templateUrl: "./patient-clustergrammer.component.html",
    styleUrls: ["./patient-clustergrammer.component.scss"]
})
export class PatientClustergrammerComponent implements OnInit {

    public tab: any;
    public patient: any;
    public criteria: any;
    public rnaSeqForm: any;
    public rnaSeqParams: any;
    private modalConfig: any;
    public genes: any;
    public ideogram: any;
    public clustergrammer: any;

    @Input() params: any;

    constructor(
        private dataService: DataService,
        private constants: Constants,
        private msg: MessageCode,
        private modalService: ModalService,
        private appState: AppStateService,
        private dataAnlysService: DataAnlysService
    ) {
    }

    ngOnInit(): void {
        console.log('---> xxxxx');
        this.init();
    }

    private init(): void {
        // this.patient = this.params.patient;
        // this.appState.patient = this.patient;

        this.tab = {
            data: [
                { value: 1, name: "RNA-Seq" }
            ],
            step: 1,
            styleClass: "tablist"
        };

        this.criteria = {};
    }

    public search(criteria: any) {
        this.criteria = criteria;
    }

    public updateRnaSeqParams(criteria: any): void {
        this.rnaSeqParams = criteria;
        console.log('O:--SUCCESS--:--Update Params--:rnaSeqParams/', this.rnaSeqParams);
        this.getOmicsAnalysis();
    }

    private getOmicsAnalysis() {
        this.clearData();

        // this.dataService
        //     .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.INDIV_CLUSTERGRAMMER, this.rnaSeqParams)
        //     .then((res: any) => {
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
            if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                const sampleOmics = res.data;
                this.prepareDataForClustergrammer(sampleOmics);
            } else {
                this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                this.modalService.show(AlertDialogComponent, 'model-sm', this.modalConfig);
            }
        });
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

    private prepareDataForClustergrammer(sampleOmics: any) {
        const req: any = {};
        req.sampleOmics = sampleOmics;
        req.sampleCluster = this.sampleCluster;
        req.geneCluster = this.geneCluster;

        this.dataAnlysService.connect('clustergrammer', req).subscribe((res: any) => {
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

    public get isHideSeqType(): boolean {
        return this.params.dataTypeAnalysis !== 'VCF';
    }
}
