import { Component, OnInit } from '@angular/core';
import { Constants } from '../../shared/services/constants';
import { ModalService } from "../../shared/services/modal.service";
import { ViewImageDialogComponent } from "../../shared/components/modal-dialog/view-image-dialog/view-image-dialog.component";

@Component({
  selector: 'app-view-biospecimen-management',
  templateUrl: './view-biospecimen-management.component.html',
  styleUrls: ['./view-biospecimen-management.component.scss']
})
export class ViewBiospecimenManagementComponent implements OnInit {

    private modalConfig: any;

    constructor(
        public constants: Constants,
        private modalService: ModalService
    ) {
    }

    ngOnInit(): void {

    }

    public getThumbnail(fiWithoutExt: string): string {
        const featureImage = `${this.constants.IMAGE_PATH}/tutorial/biospecimen-management/${fiWithoutExt}.png`;
        return featureImage;
    }

    private getImage(fiWithoutExt: string): string {
        const featureImage = `${this.constants.IMAGE_PATH}/tutorial/biospecimen-management/${fiWithoutExt}.png`;
        return featureImage;
    }

    public viewImage(title: string, fiWithoutExt: string): void {
        const imgUrl = this.getImage(fiWithoutExt);
        this.modalConfig = { title: title, imgUrl: imgUrl };
        this.modalService.show(ViewImageDialogComponent, "modal-xl", this.modalConfig);
    }

}
