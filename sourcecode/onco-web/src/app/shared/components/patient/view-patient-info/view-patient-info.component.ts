import { Component, OnInit, Input } from "@angular/core";

@Component({
    selector: "app-view-patient-info",
    templateUrl: "./view-patient-info.component.html",
    styleUrls: ["./view-patient-info.component.scss"]
})
export class ViewPatientInfoComponent implements OnInit {

    public patient: any;

    @Input() public data: any;

    constructor() {

    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        if (this.data) {
            this.patient = this.data;
        }
    }
}
