import { Component, OnInit, Input, Output, EventEmitter } from "@angular/core";
import { FormBuilder } from "@angular/forms";
import { ValidationService } from "../../../../shared/services/validation.service";
import { DataService } from "../../../../shared/services/data.service";
import { Constants } from "../../../../shared/services/constants";
import { MessageCode } from "../../../../shared/services/message-code";
import { UtilService } from "../../../../shared/services/util.service";
import { ModalService } from "../../../../shared/services/modal.service";
import { ClusterConfigFormModalComponent } from "../../../../shared/components/omics-analysis/cluster-config-form-modal/cluster-config-form-modal.component";

declare var $: any;

@Component({
    selector: "app-rna-seq-analysis-form",
    templateUrl: "./rna-seq-analysis-form.component.html",
    styleUrls: ["./rna-seq-analysis-form.component.scss"]
})
export class RnaSeqAnalysisFormComponent implements OnInit {

    private modalConfig: any;
    public rnaSeqForm: any;
    public submitted: boolean;
    public clusterConfig: any;

    public ddl: any = {
        sampleRnaSeqList: [],
        cancerGeneGroups: []
    };

    @Input() public criteria: any;
    @Output() updatedChange = new EventEmitter();

    constructor(
        private fb: FormBuilder,
        private dataService: DataService,
        private constants: Constants,
        private msg: MessageCode,
        private util: UtilService,
        private modalService: ModalService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        console.log('---> NEDA RNA-SEQ');
        this.submitted = false;
        this.buildForm();

        this.clusterConfig = {};
        this.clusterConfig.nCluster = '3';
        this.clusterConfig.clusterAlgo = { code: 'kmean', name: 'K-means clustering' };

        const p1 = this.getSampleRnaSeqList();
        const p2 = this.getCancerGeneGroupList();

        Promise.all([p1, p2]).then((values) => {
            values.forEach((v) => { console.log(v); });
        }).catch(err => {
            console.log(err);
        }).finally(() => {
            setTimeout(() => {
                this.initMultiSelect();
            }, 500);
        });
    }

    private buildForm(): void {
        this.rnaSeqForm = this.fb.group({
            sampleRnaSeqIds: ["", [ValidationService.requiredValidator]],
            cancerGeneGrpId: ["", [ValidationService.requiredValidator]]
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

        if (this.rnaSeqForm.valid) {
            const cancerGeneGrpId = this.rnaSeqForm.value.cancerGeneGrpId;

            if (!this.util.isNullOrEmpty(cancerGeneGrpId)) {
                criteria.cancerGeneGroup = {};
                criteria.cancerGeneGroup.id = cancerGeneGrpId;
            }

            criteria.clusterConfig = this.clusterConfig;
            criteria.sampleRnaSeqIds = this.rnaSeqForm.value.sampleRnaSeqIds;
            console.log('O:--SUCCESS--:--Update Params--:rnaSeqParams/', criteria);
            this.updatedChange.emit(criteria);
        }
    }

    public editConfig(): void {
        this.modalConfig = { selected: this.clusterConfig };
        const modalRef = this.modalService.show(ClusterConfigFormModalComponent, 'modal-md', this.modalConfig);
        modalRef.subscribe((config: any) => {
            console.log('---> config : ', config);
            this.clusterConfig = config;
        });
    }

    private getSampleRnaSeqList(): Promise<{}> {
        const p = new Promise((resolve) => {
            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_ACTIVE_SAMPLE_RNA_SEQ_LIST, {})
                .then((res: any) => {
                console.log('--->  res : ', res);
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl.sampleRnaSeqList = res.data;
                }

                resolve(`service.getSampleRnaSeqList() has been resolved.`);
            });
        });

        return p;
    }

    private getCancerGeneGroupList(): Promise<{}> {
        const p = new Promise((resolve) => {
            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.GET_CANCER_GENE_GROUPS, {})
                .then((res: any) => {
                console.log('--->  res : ', res);
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl.cancerGeneGroups = res.data.items;
                }

                resolve(`service.getCancerGeneGroupList() has been resolved.`);
            });
        });

        return p;
    }
}
