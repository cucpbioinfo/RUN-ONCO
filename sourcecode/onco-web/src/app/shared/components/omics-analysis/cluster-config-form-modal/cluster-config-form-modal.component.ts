import { Component, OnInit, AfterViewInit } from "@angular/core";
import { BsModalRef } from "ngx-bootstrap/modal";
import { FormBuilder, FormGroup, FormControl } from "@angular/forms";
import { Subject } from "rxjs";
import { ValidationService } from "../../../services/validation.service";
import { Constants } from "../../../services/constants";
import { DataService } from "../../../services/data.service";
import { MessageCode } from "../../../services/message-code";
import { UtilService } from "../../../services/util.service";

@Component({
    selector: "app-cluster-config-form-modal",
    templateUrl: "./cluster-config-form-modal.component.html",
    styleUrls: ["./cluster-config-form-modal.component.scss"]
})
export class ClusterConfigFormModalComponent implements OnInit, AfterViewInit {

    public submitted: boolean;
    public clusterForm: FormGroup;
    public onClose: Subject<any>;
    public selected: any;

    public ddl: any = {
        clusterAlgorithms: []
    };

    private dataMapper: Map<string, string> = new Map<string, string>(
        [
            ['clusterAlgorithms', this.constants.SERVICE_NAME.GET_CLUSTER_ALGORITHMS]
        ]
    );

    constructor(
        public fb: FormBuilder,
        public bsModalRef: BsModalRef,
        public constants: Constants,
        public dataService: DataService,
        public msg: MessageCode,
        public util: UtilService
    ) {
        this.onClose = new Subject();
    }

    ngOnInit(): void {
        this.init();
    }

    ngAfterViewInit(): void {
        if (this.selected) {
            const values: any = {};
            values.nCluster = this.selected.nCluster;
            values.clusterAlgo = this.selected.clusterAlgo.code;
            this.clusterForm.patchValue(values);
        }
    }

    private init(): void {
        this.buildForm();

        this.dataMapper.forEach((value: string, key: string) => {
            this.dataService.connect(`${this.constants.CTX.SECURITY}/${this.constants.CTX.MASTER_DATA_MANAGEMENT}`, value, {}).then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl[key] = res.data.items;
                }
            });
        });
    }

    private buildForm(): void {
        this.clusterForm = this.fb.group({
            nCluster: new FormControl('', [ValidationService.requiredValidator, ValidationService.clusterNoValidator]),
            clusterAlgo: new FormControl('', [ValidationService.requiredValidator]),
        });
    }

    public onSave() {
        this.submitted = true;

        if (this.clusterForm.valid) {
            this.onClose.next(this.config);
            this.bsModalRef.hide();
        }
    }

    public trackByFn(index, item) {
        return index;
    }

    private get config() {
        const data: any = {};
        data.nCluster = this.clusterForm.value.nCluster;

        if (!this.util.isNullOrEmpty(this.clusterForm.value.clusterAlgo)) {
            data.clusterAlgo = this.ddl.clusterAlgorithms.find((x => x.code === this.clusterForm.value.clusterAlgo));
        }

        return data;
    }
}
