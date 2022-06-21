import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "../../services/auth.service";
import { FormBuilder, FormGroup, FormControl } from "@angular/forms";
import { ValidationService } from "../../services/validation.service";
import { MessageCode } from "../../services/message-code";
import { ModalService } from "../../services/modal.service";
import { AlertDialogComponent } from "../../components/modal-dialog/alert-dialog/alert-dialog.component";
import { AppStateService } from "../../services/app-state.service";
import { DateService } from "../../services/date.service";
import { Constants } from "../../services/constants";

@Component({
    selector: "app-login-form",
    templateUrl: "./login-form.component.html",
    styleUrls: ["./login-form.component.scss"]
})
export class LoginFormComponent implements OnInit {

    public loginForm: FormGroup;
    public submitted: boolean;
    private modalConfig: any;

    public featureImages: any = {
        'CLINICAL_DATA': 'run-onco-clinical-data-management',
        'BIOSPECIMEN': 'run-onco-biospecimen-management',
        'OMICS_MANAGEMENT': 'run-onco-omics-management',
        'CYTOSCAPE': 'run-onco-cytoscape',
        'CLUSTERGRAMMER': 'run-onco-clustergrammer',
        'CONNECT_TO_ONCOKB': 'run-onco-connecting-to-oncokb'
    };

    constructor(
        public router: Router,
        private fb: FormBuilder,
        public authService: AuthService,
        private msg: MessageCode,
        private modalService: ModalService,
        public appState: AppStateService,
        public dateService: DateService,
        public constants: Constants
    ) {
    }

    ngOnInit(): void {
        this.init();
    }

    public onLoggedin(): void {
        this.submitted = true;

        if (this.loginForm.valid) {
            const username = this.loginForm.value.username;
            const password = this.loginForm.value.password;

            this.authService.login(username, password).subscribe(res => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.appState.userInfo = res.data;
                    this.router.navigate(["/patient-search"]);
                } else {
                    this.modalConfig = {title: "Message", message: res.responseStatus.responseMessage};
                    this.modalService.show(AlertDialogComponent, "modal-sm", this.modalConfig);
                }
            });

        } else {
            this.modalConfig = {title: "Message", message: 'Please enter all required field.'};
            this.modalService.show(AlertDialogComponent, "modal-sm", this.modalConfig);
        }
    }

    private init(): void {
        this.submitted = false;
        this.buildForm();
    }

    private buildForm(): void {
        this.loginForm = this.fb.group({
            username: new FormControl("", [ValidationService.requiredValidator]),
            password: new FormControl("", [ValidationService.requiredValidator])
        });
    }
}
