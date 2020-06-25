import { Component, OnInit } from "@angular/core";
import { FormBuilder, FormGroup, FormControl } from "@angular/forms";
import { ValidationService } from "../shared/services/validation.service";
import { DataService } from "../shared/services/data.service";
import { Constants } from "../shared/services/constants";
import { MessageCode } from "../shared/services/message-code";
import { AlertDialogComponent } from "../shared/components/modal-dialog/alert-dialog/alert-dialog.component";
import { ModalService } from "../shared/services/modal.service";
import { Router } from "@angular/router";
import { DownloadService } from "../shared/services/download.service";
import { UtilService } from "../shared/services/util.service";

@Component({
    selector: "app-download",
    templateUrl: "./download.component.html",
    styleUrls: ["./download.component.scss"]
})
export class DownloadComponent implements OnInit {

    public downloadForm: FormGroup;
    public submitted: boolean;
    private modalConfig: any;
    public downloadDto: any;

    constructor(
        private fb: FormBuilder,
        private dataService: DataService,
        private constants: Constants,
        private msg: MessageCode,
        private modalService: ModalService,
        private router: Router,
        private downloadService: DownloadService,
        private util: UtilService
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.submitted = false;
        this.buildForm();
    }

    private buildForm(): void {
        this.downloadForm = this.fb.group({
            firstName: new FormControl("", [ValidationService.requiredValidator]),
            lastName: new FormControl("", [ValidationService.requiredValidator]),
            email: new FormControl("", [ValidationService.requiredValidator, ValidationService.emailValidator]),
            affiliate: new FormControl("", [ValidationService.requiredValidator]),
            agree: new FormControl(false, [ValidationService.requiredValidator])
        });
    }

    public goToPage(): void {
        this.router.navigate(['/']);
    }

    public onSubmit(): void {
        if (!this.downloadDto) {
            this.submitted = true;

            if (this.downloadForm.valid) {
                this.dataService.connect('', this.constants.SERVICE_NAME.REGISTER_TO_DOWNLOAD, this.req)
                    .then((res: any) => {
                        console.log("---> res: ", res);

                        if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                            this.downloadDto = res.data;
                            this.modalConfig = { title: 'Message', message: 'Save successfully.' };
                            this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig).subscribe(() => {
                                // XXXX
                            });
                        } else {
                            this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                            this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
                        }
                    });
            }
        }
    }

    public onDownload(): void {
        const ctx = '';
        const serviceName = this.constants.SERVICE_NAME.DOWNLOAD_SOURCE_CODE;
        this.downloadService.downloadFile(ctx, serviceName, this.downloadDto).subscribe((res: any) => {
            // console.log('---> res : ', res);
            const disposition = res.headers.get('content-disposition');
            if (disposition && disposition.indexOf('attachment') !== -1) {
                const regex = /filename[^;=\n]*=((['"]).*?\2|[^;\n]*)/;
                const matches = regex.exec(disposition);
                if (matches != null && matches[1]) {
                    const filename = matches[1].replace(/['"]/g, '');
                    const content = res._body;
                    this.util.saveFile(content, filename, 'application/octet-stream');
                }
            }
        });
    }

    private get req(): any {
        const req: any = {};
        req.firstName = this.downloadForm.value.firstName;
        req.lastName = this.downloadForm.value.lastName;
        req.email = this.downloadForm.value.email;
        req.affiliate = this.downloadForm.value.affiliate;
        req.isAgree = this.downloadForm.value.agree ? this.constants.YES : this.constants.NO;
        return req;
    }
}
