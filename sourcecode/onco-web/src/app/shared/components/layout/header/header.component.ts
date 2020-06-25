import { Component, OnInit } from "@angular/core";
import { Constants } from "../../../services/constants";
import { MessageCode } from "../../../services/message-code";
import { ConfirmDialogComponent } from "../../../components/modal-dialog/confirm-dialog/confirm-dialog.component";
import { ModalService } from "../../../services/modal.service";
import { Router } from "@angular/router";
import { AppStateService } from "../../../services/app-state.service";

@Component({
    selector: "app-header",
    templateUrl: "./header.component.html",
    styleUrls: ["./header.component.scss"]
})
export class HeaderComponent implements OnInit {

    private modalConfig: any;

    constructor(public constants: Constants,
        public msg: MessageCode,
        private modalService: ModalService,
        private router: Router,
        private appState: AppStateService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
    }

    public onLoggedout(): void {
        this.modalConfig = { title: 'Message', message: 'Confirm logout?' };
        const modalRef = this.modalService.show(ConfirmDialogComponent, 'modal-sm', this.modalConfig);

        modalRef.subscribe((confirm: any) => {
            if (confirm) {
                localStorage.removeItem('token');
                this.router.navigate(['/login']);
            }
        });
    }

    public get menuGroups(): any {
        if (this.appState.userInfo && this.appState.userInfo.permission.menuGroups) {
            return this.appState.userInfo.permission.menuGroups;
        }
    }

    public trackByFn(index, item) {
        return index;
    }
}
