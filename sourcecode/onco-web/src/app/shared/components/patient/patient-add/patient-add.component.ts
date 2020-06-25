import { Component, OnInit, Input, Output, EventEmitter } from '@angular/core';
import { TypeaheadMatch } from "ngx-bootstrap/typeahead";
import { Observable } from "rxjs";
import { DataService } from "../../../../shared/services/data.service";
import { FormBuilder, FormGroup } from '@angular/forms';
import { Constants } from "../../../../shared/services/constants";
import { ValidationService } from 'src/app/shared/services/validation.service';
import { MessageCode } from "../../../../shared/services/message-code";
import { Router } from "@angular/router";
import { UtilService } from "../../../../shared/services/util.service";

@Component({
  selector: 'app-patient-add',
  templateUrl: './patient-add.component.html',
  styleUrls: ['./patient-add.component.scss']
})
export class PatientAddComponent implements OnInit {

    private patient: any;
    public submitted = false;
    public searchForm: FormGroup;

    @Input() page: string;
    @Output() selectChanged = new EventEmitter();

    typeaheadLoading: boolean;
    noResult = true;
    dataSource: Observable<any>;
    patientList: any;

    constructor(
        private msg: MessageCode,
        private constants: Constants,
        private dataService: DataService,
        private fb: FormBuilder,
        private router: Router,
        private util: UtilService
    ) {
        this.dataSource = Observable.create((observer: any) => {
            this.clearForm();

            const req = {
                patient: {
                    hn: this.searchForm.value.hn
                },
                paging: { startIndex: 0, fetchSize: 10 }
            };

            this.dataService.connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.SEARCH_PATIENT_BY_REF, req, true).then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code
                    || res.responseStatus.responseCode !== this.msg.ERROR_DATA_NOT_FOUND.code) {
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
        this.buildForm();
    }

    private buildForm(): void {
        this.searchForm = this.fb.group({
            id: ["", [ValidationService.requiredValidator]],
            hn: ["", [ValidationService.requiredValidator]]
        });
    }

    public changeTypeaheadLoading(e: boolean): void {
        this.typeaheadLoading = e;
    }

    public typeaheadOnSelect(e: TypeaheadMatch): void {
        const selected = this.patientList.find((x: any) => x.hn === e.value);
        console.log('----: selected: ***', selected);

        if (selected) {
            this.noResult = false;
            this.patient = selected;
            this.searchForm.patchValue({id: selected.id});
        }
    }

    public typeaheadNoResults(event: any): void {
        this.noResult = event;

        if (this.noResult) {
            this.searchForm.patchValue({id: ''});
            this.patient = undefined;
        }
        console.log('---> this.noResult : ', this.noResult);
    }

    public onTextChanged(): void {
        if (!this.util.isEmptyObject(this.searchForm.value.hn)) {
            this.patient = undefined;
            this.searchForm.patchValue({id: ''});
        }
    }

    public onAdd(): void {
        this.submitted = true;

        if (this.searchForm.valid) {
            this.selectChanged.emit({ noResult: this.noResult, patient: this.patient });
        }
    }

    public goToPage(): void {
        this.router.navigate([this.page]);
    }

    private clearForm(): void {
        this.noResult = true;
        this.patient = undefined;
        this.submitted = false;
    }
}
