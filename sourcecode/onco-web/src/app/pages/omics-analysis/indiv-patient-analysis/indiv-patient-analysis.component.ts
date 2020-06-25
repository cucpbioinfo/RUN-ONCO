import { Component, OnInit } from "@angular/core";
import { TypeaheadMatch } from "ngx-bootstrap/typeahead";
import { Observable } from "rxjs";
import { DataService } from "../../../shared/services/data.service";
import { ValidationService } from 'src/app/shared/services/validation.service';
import { MessageCode } from "../../../shared/services/message-code";
import { FormBuilder } from '@angular/forms';
import { Constants } from "../../../shared/services/constants";
import { AppStateService } from "../../../shared/services/app-state.service";
import { UtilService } from "../../../shared/services/util.service";

declare var $: any;

@Component({
    selector: "app-indiv-patient-analysis",
    templateUrl: "./indiv-patient-analysis.component.html",
    styleUrls: ["./indiv-patient-analysis.component.scss"]
})
export class IndivPatientAnalysisComponent implements OnInit {

    public patient: any;
    public selectedComponent: any;
    public analysisForm: any;
    public submitted: boolean;
    public breadcrumbs: any;
    private fields = ['patientId', 'hn'];
    private userInfo: any;

    typeaheadLoading: boolean;
    noResult = true;
    dataSource: Observable<any>;
    patientList: any;

    constructor(private msg: MessageCode,
        private fb: FormBuilder,
        private dataService: DataService,
        private constants: Constants,
        private appState: AppStateService,
        private util: UtilService
    ) {
        this.dataSource = Observable.create((observer: any) => {
            this.submitted = true;

            const req = {
                patient: {
                    hn: this.analysisForm.value.hn
                },
                paging: { startIndex: 0, fetchSize: 10 }
            };

            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.SEARCH_PATIENT_BY_REF, req, true).then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.patientList = res.data;

                    const names = [];
                    res.data.forEach((x: any) => {
                        names.push(x.hn);
                    });
                    observer.next(names);
                } else {
                    observer.next();
                }
            });
        });
    }

    ngOnInit(): void {
        this.init();
    }

    private init(): void {
        this.submitted = false;
        this.buildForm();

        this.breadcrumbs = [
            {
                name: 'Patient Analysis',
                url: '/indiv-patient-analysis',
                active: true
            }
        ];

        this.userInfo = this.appState.userInfo;
        this.getDynamicComponents();
        this.initMultiSelect();
    }

    public changeTypeaheadLoading(e: boolean): void {
        this.typeaheadLoading = e;
    }

    public typeaheadOnSelect(e: TypeaheadMatch): void {
        const selected = this.patientList.find((x: any) => x.hn === e.value);

        if (selected) {
            this.noResult = false;
            this.patient = selected;
            this.analysisForm.patchValue({patientId: selected.id});
        }
    }

    public typeaheadNoResults(event: any): void {
        this.noResult = event;

        if (this.noResult) {
            this.analysisForm.patchValue({patientId: ''});
            this.patient = undefined;
        }
        console.log('---> this.noResult : ', this.noResult);
    }

    public onTextChanged(): void {
        if (!this.util.isEmptyObject(this.analysisForm.value.hn)) {
            this.analysisForm.patchValue({patientId: ''});
            this.patient = undefined;
        }
    }

    private buildForm(): void {
        this.analysisForm = this.fb.group({
            patientId: [""],
            hn: [""],
            component: ["", [ValidationService.requiredValidator]]
        });
    }

    public search(event: any) {
        this.patient = event;
    }

    public onSelectedChanged(): void {
        // Clear validation
        this.fields.forEach((x: string) => {
            this.analysisForm.get(x).clearValidators();
            this.analysisForm.get(x).updateValueAndValidity();
        });

        const component = this.componentList.find((x: any) => x.componentName === this.analysisForm.value.component);
        console.log('---> component: ', component);
        this.selectedComponent = component;

        if (this.selectedComponent.dataTypeAnalysis === 'VCF') {
            this.fields.forEach((x: string) => {
                this.analysisForm.get(x).setValidators([ValidationService.requiredValidator]);
                this.analysisForm.get(x).updateValueAndValidity();
            });
        }
    }

    private initMultiSelect(): void {
        $(function() { $('.selectpicker').selectpicker(); });
    }

    public trackByFn(index, item) {
        return index;
    }

    public get componentList() {
        const dynamicComponents = this.appState.dynamicComponents.filter((x: any) =>
            this.userInfo.permission.dynamicContents.indexOf(x.componentName) !== -1
        );
        return dynamicComponents;
    }

    public get isAvailable(): boolean {
        if (this.analysisForm.valid) {
            return this.selectedComponent && (this.patient || this.selectedComponent.dataTypeAnalysis !== 'VCF');
        }

        return false;
    }

    private getDynamicComponents() {
        if (!this.appState.dynamicComponents) {
            this.dataService.connect('', this.constants.SERVICE_NAME.GET_DYNAMIC_CONTENT_LIST, {})
            .then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.appState.dynamicComponents = res.data.items;
                } else if (res.responseStatus.responseCode === this.msg.ERROR_DATA_NOT_FOUND.code) {
                    this.appState.dynamicComponents = {};
                }
            });
        }
    }
}
