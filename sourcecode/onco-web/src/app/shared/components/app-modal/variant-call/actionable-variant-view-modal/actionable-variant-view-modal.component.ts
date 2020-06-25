import { Component, OnInit, AfterViewInit } from "@angular/core";
import { BsModalRef } from "ngx-bootstrap/modal";
import { Subject } from "rxjs";
import { MessageCode } from "../../../../services/message-code";
import { Dictionary } from "../../../../bean/dictionary";
import { UtilService } from "../../../../services/util.service";
import { Constants } from "../../../../services/constants";
import { TranslateService } from "@ngx-translate/core";

@Component({
    selector: "app-actionable-variant-view-modal",
    templateUrl: "./actionable-variant-view-modal.component.html",
    styleUrls: ["./actionable-variant-view-modal.component.scss"]
})
export class ActionableVariantViewModalComponent implements OnInit, AfterViewInit {

    public onClose: Subject<any>;
    public variantCall: any;
    public annotatedVariantList: any;
    public dictAnnovar: Dictionary;

    constructor(public bsModalRef: BsModalRef,
        public msg: MessageCode,
        public util: UtilService,
        public constants: Constants,
        private translate: TranslateService
    ) {
        this.onClose = new Subject();
    }

    ngOnInit(): void {
        this.init();
    }

    ngAfterViewInit(): void {
        this.util.initTooltips();
    }

    private init(): void {
        this.dictAnnovar = {};
        this.prepareAnnotatedVariant();
    }

    public trackByFn(index, item) {
        return index;
    }

    private prepareAnnotatedVariant() {
        this.annotatedVariantList.forEach((x: any) => {
            this.dictAnnovar[x.gene] = this.dictAnnovar[x.gene] || [];
            x.drugInArr = this.toArray(x.drugs);
            this.dictAnnovar[x.gene].push(x);
        });
    }

    public get geneList(): any {
        return  this.dictAnnovar ? Object.keys(this.dictAnnovar) : [];
    }

    public onNavigate(url: string, item: string, lowercase = false): void {
        let s = item;
        if (lowercase) {
            s = item.replace(/(\s*[+]\s*)/gm, ' ').toLowerCase();
        }
        const encodeUri = encodeURIComponent(s);
        console.log('---> encodeUri : ' + encodeUri);

        this.util.openNewWindow(`${url}${encodeUri}`);
    }

    private toArray(str: string) {
        let arr = [];
        const res = str.trim().replace(/(\s*[,]\s*)/gm, ',');

        if (res.indexOf(',') !== -1) {
            arr = res.split(',');
        } else {
            arr = [res];
        }

        return arr;
    }

    public getOncoKbLevelStyleClass(level: string): string {
        return `level-icon level-${level}`;
    }

    public getOncoKbLevelInfo(level: string): string {
        return this.translate.instant(`evidence.level${level}`);
    }

    public get featureType(): string {
        if (this.variantCall) {
            if (this.isGene) {
                return this.variantCall.featureType;
            }

            return 'RefSeq';
        }

        return 'Isoform';
    }

    public get feature(): string {
        if (this.variantCall) {
            return this.variantCall.feature;
        }

        return this.annotatedVariantList[0].isoform;
    }

    public get isGene(): boolean {
        const isGene = this.util.isGene(this.variantCall.feature);
        return isGene;
    }
}
