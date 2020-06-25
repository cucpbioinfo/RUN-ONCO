import { Component, OnInit, Input } from "@angular/core";
import { Constants } from "../../../services/constants";
import { PathoViewModalComponent } from "../../../components/app-modal/clinical-data/patho-view-modal/patho-view-modal.component";
import { ModalService } from "../../../services/modal.service";

@Component({
    selector: "app-view-biospecimen",
    templateUrl: "./view-biospecimen.component.html",
    styleUrls: ["./view-biospecimen.component.scss"]
})
export class ViewBiospecimenComponent implements OnInit {

    public biospecimen: any;
    private modalConfig: any;

    public isShowBloodType = false;
    public isShowTissueType = false;
    public isShowCellType = false;

    @Input() public data: any;

    constructor(
        public constants: Constants,
        private modalService: ModalService
    ) {

    }

    ngOnInit() {
        this.init();
    }

    private init(): void {
        if (this.data) {
            this.biospecimen = this.data;

            if (this.biospecimen.sampleType) {
                const sampleTypeCode = this.biospecimen.sampleType.code;

                if (sampleTypeCode === this.constants.SAMPLE_TYPE.BLOOD) {
                    this.isShowBloodType = true;
                } else if (sampleTypeCode === this.constants.SAMPLE_TYPE.TISSUE) {
                    this.isShowTissueType = true;
                } else if (sampleTypeCode === this.constants.SAMPLE_TYPE.CELL) {
                    this.isShowCellType = true;
                }
            }
        }
    }

    public viewPatho(item: any): void {
        this.modalConfig = { selected: item };
        console.log('I:--START--:--InitView Patho--:modalConfig/', this.modalConfig);
        const modalRef = this.modalService.show(PathoViewModalComponent, 'modal-lg', this.modalConfig);
        modalRef.subscribe((result: any) => {
            console.log('---> result : ', result);
        });
    }
}
