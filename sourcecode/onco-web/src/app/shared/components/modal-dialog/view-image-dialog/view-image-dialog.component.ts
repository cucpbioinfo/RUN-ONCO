import { Component, OnInit } from '@angular/core';
import { BsModalRef } from "ngx-bootstrap/modal";
import { Subject } from "rxjs";

@Component({
  selector: 'app-view-image-dialog',
  templateUrl: './view-image-dialog.component.html',
  styleUrls: ['./view-image-dialog.component.scss']
})
export class ViewImageDialogComponent implements OnInit {

    title: string;
    imgUrl: string;
    messsage: string;

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
