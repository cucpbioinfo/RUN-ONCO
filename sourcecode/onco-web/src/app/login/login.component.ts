import { Component, OnInit } from "@angular/core";
import { Router } from "@angular/router";
import { AuthService } from "../shared/services/auth.service";
import { FormBuilder, FormGroup, FormControl } from "@angular/forms";
import { ValidationService } from "../shared/services/validation.service";
import { ModalService } from "../shared/services/modal.service";
import { AppStateService } from "../shared/services/app-state.service";
import { DateService } from "../shared/services/date.service";
import { Constants } from "../shared/services/constants";
import { ViewImageDialogComponent } from "../shared/components/modal-dialog/view-image-dialog/view-image-dialog.component";

// import * as pack from '../../../package.json';
// import * as env from '../../environments/environment.prod';

@Component({
    selector: "app-login",
    templateUrl: "./login.component.html",
    styleUrls: ["./login.component.scss"]
})
export class LoginComponent implements OnInit {

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
        private modalService: ModalService,
        public appState: AppStateService,
        public dateService: DateService,
        public constants: Constants
    ) {
    }

    ngOnInit() {
        this.init();
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

    public getThumbnail(fiWithoutExt: string): string {
        const featureImage = `${this.constants.IMAGE_PATH}/feature/${fiWithoutExt}-thumbnail.png`;
        return featureImage;
    }

    private getImage(fiWithoutExt: string): string {
        const featureImage = `${this.constants.IMAGE_PATH}/feature/${fiWithoutExt}.png`;
        return featureImage;
    }

    public viewImage(title: string, fiWithoutExt: string): void {
        const imgUrl = this.getImage(fiWithoutExt);
        this.modalConfig = { title: title, imgUrl: imgUrl };
        this.modalService.show(ViewImageDialogComponent, "modal-xl", this.modalConfig);
    }
}
