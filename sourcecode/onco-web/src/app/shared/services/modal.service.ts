import { Injectable } from '@angular/core';
import { BsModalService } from 'ngx-bootstrap/modal';

@Injectable()
export class ModalService {

    constructor(private modalService: BsModalService) {
    }

    show(modalComponent: any, modalSize: string, initialState?: any) {
        const bsModalRef = this.modalService.show(modalComponent, { initialState, class: modalSize });
        return bsModalRef.content.onClose;
    }
}
