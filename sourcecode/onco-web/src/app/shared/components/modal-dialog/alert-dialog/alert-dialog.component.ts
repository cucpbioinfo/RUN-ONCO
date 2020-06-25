import { Component, OnInit } from "@angular/core";
import { BsModalRef } from "ngx-bootstrap/modal";
import { Subject } from "rxjs";

@Component({
    selector: "app-alert-dialog",
    templateUrl: "./alert-dialog.component.html",
    styleUrls: ["./alert-dialog.component.scss"]
})
export class AlertDialogComponent implements OnInit {

    title: string;
    message: string;

    public onClose: Subject<boolean>;

    constructor(public bsModalRef: BsModalRef) {
        this.onClose = new Subject();
    }

    ngOnInit() {}

    public close(): void {
        this.onClose.next(true);
        this.bsModalRef.hide();
    }
}
