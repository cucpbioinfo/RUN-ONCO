import { Component, OnInit } from "@angular/core";
import { BsModalRef } from "ngx-bootstrap/modal";

@Component({
    selector: "app-patho-view-modal",
    templateUrl: "./patho-view-modal.component.html",
    styleUrls: ["./patho-view-modal.component.scss"]
})
export class PathoViewModalComponent implements OnInit {

    public patho: any;
    public selected: any;

    constructor(
        public bsModalRef: BsModalRef
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.patho = this.selected;
    }
}
