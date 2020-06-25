import { Component, OnInit, AfterViewInit, ChangeDetectorRef } from "@angular/core";
import { FormBuilder, FormGroup, FormControl } from "@angular/forms";
import { Constants } from "../../../../services/constants";
import { ValidationService } from "../../../../services/validation.service";
import { BsModalRef } from "ngx-bootstrap/modal";
import { Subject, Observable, from } from "rxjs";
import { TypeaheadMatch } from "ngx-bootstrap/typeahead";
import { MessageCode } from "../../../../services/message-code";
import { UtilService } from "../../../../services/util.service";
import { ModalService } from "../../../../services/modal.service";
import { AlertDialogComponent } from "../../../../components/modal-dialog/alert-dialog/alert-dialog.component";
import { DataService } from "../../../../services/data.service";
import { DateService } from "../../../../services/date.service";
import { Dictionary } from '../../../../bean/dictionary';

declare var $: any;

@Component({
    selector: "app-patho-form-modal",
    templateUrl: "./patho-form-modal.component.html",
    styleUrls: ["./patho-form-modal.component.scss"]
})
export class PathoFormModalComponent implements OnInit, AfterViewInit {

    public submitted: boolean;
    public pathoForm: FormGroup;
    public onClose: Subject<any>;
    public selected: any;
    private modalConfig: any;
    public minDate: any;
    public dt: Date;
    public temp: Dictionary = {};

    typeaheadLoading: boolean;
    noResult = true;
    dataSource: Observable<any>;
    morphologyList: any;

    public ddl: any = {
        tissueTypeList: [],
        tissueProcedureList: [],
        histologicGradeList: []
    };

    private dataMapper: Map<string, string> = new Map<string, string>(
        [
            ['tissueProcedureList', this.constants.SERVICE_NAME.GET_TISSUE_PROCEDURE_LIST],
            ['tissueTypeList', this.constants.SERVICE_NAME.GET_TISSUE_TYPE_LIST],
            ['histologicGradeList', this.constants.SERVICE_NAME.GET_HISTOLOGIC_GRADE_LIST]
        ]
    );

    constructor(
        public fb: FormBuilder,
        public constants: Constants,
        public bsModalRef: BsModalRef,
        public msg: MessageCode,
        public util: UtilService,
        private cd: ChangeDetectorRef,
        private modalService: ModalService,
        private dataService: DataService,
        private dateService: DateService
    ) {
        this.onClose = new Subject();

        this.dataSource = Observable.create((observer: any) => {
            const req = {
                icd: {
                    name: this.pathoForm.value.morphologyDesc,
                    // version: 'ICD-O-3 (2000)',
                    isDefault: this.constants.YES,
                    status: this.constants.STATUS_ACTIVE
                },
                paging: { startIndex: 0, fetchSize: 10 }
            };

            this.dataService.filter(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.FILTER_ICD_O, req, true).subscribe((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.morphologyList = res.data.items;

                    const names = [];
                    res.data.items.forEach((x: any) => {
                        names.push(x.name);
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

    ngAfterViewInit(): void {
        if (this.selected) {
            const values: any = {};
            values.pathoNo = this.selected.pathologyNo;
            values.resectionBiopsySite = this.selected.resectionBiopsySite;
            values.status = this.selected.status;
            values.id = this.selected.id;

            if (this.selected.tissueReportDate) {
                values.tissueReportDate = new Date(this.selected.tissueReportDate);
            }
            if (this.selected.tissueProcedure) {
                values.tissueProcedureCode = this.selected.tissueProcedure.code;
            }
            if (this.selected.tissueType) {
                values.tissueTypeCode = this.selected.tissueType.code;
            }
            if (this.selected.morphology) {
                values.morphologyCode = this.selected.morphology.code;
                values.morphologyDesc = this.selected.morphology.name;
            }
            if (this.selected.histologicGrade) {
                values.histologicGradeCode = this.selected.histologicGrade.code;
            }

            this.pathoForm.patchValue(values);
            this.cd.detectChanges();
        }
    }

    public changeTypeaheadLoading(e: boolean): void {
        this.typeaheadLoading = e;
    }

    public typeaheadOnSelect(e: TypeaheadMatch): void {
        const selected = this.morphologyList.find((x: any) => x.name === e.value);
        console.log('---> selected: ', selected);

        if (selected) {
            this.noResult = false;
            this.pathoForm.patchValue({morphologyCode: selected.code});
        }
    }

    public typeaheadNoResults(event: any): void {
        this.noResult = event;

        if (this.noResult) {
            this.pathoForm.patchValue({morphologyCode: ''});
        }
    }

    public onTextChanged(): void {
        if (!this.util.isEmptyObject(this.pathoForm.value.morphologyDesc)) {
            this.pathoForm.patchValue({morphologyCode: ''});
        }
    }

    public onSave() {
        this.submitted = true;

        if (this.pathoForm.valid) {
            this.onClose.next(this.patho);
            this.bsModalRef.hide();
        }
    }

    private init(): void {
        this.submitted = false;
        this.buildForm();

        this.dt = this.dateService.getCurrentDate();
        this.minDate = this.dateService.parseDate('1900-01-01');

        this.dataMapper.forEach((value: string, key: string) => {
            const criteria: any = this.getMFilter(key);

            this.dataService.connect(`${this.constants.CTX.SECURITY}/${this.constants.CTX.MASTER_DATA_MANAGEMENT}`, value, criteria).then((res: any) => {
                if (res.responseStatus.responseCode === this.msg.SUCCESS.code) {
                    this.ddl[key] = res.data.items;
                }
            });
        });

        this.onScriptLoad();
    }

    private getMFilter(ctrlName: any): any {
        let criteria: any = {};

        if (ctrlName === 'tissueTypeList') {
            criteria = { type: 1 };
        }

        return criteria;
    }

    private buildForm(): void {
        this.pathoForm = this.fb.group({
            pathoNo: new FormControl('', [ValidationService.requiredValidator]),
            tissueReportDate: new FormControl('', [ValidationService.requiredValidator]),
            resectionBiopsySite: new FormControl('', [ValidationService.requiredValidator]),
            tissueProcedureCode: new FormControl('', [ValidationService.requiredValidator]),
            tissueTypeCode: new FormControl('', [ValidationService.requiredValidator]),
            morphologyCode: new FormControl('', [ValidationService.requiredValidator]),
            morphologyDesc: new FormControl('', [ValidationService.requiredValidator]),
            histologicGradeCode: new FormControl('', [ValidationService.requiredValidator]),
            status: new FormControl(this.constants.STATUS_ACTIVE),
            id: new FormControl('')
        });
    }

    public trackByFn(index, item) {
        return index;
    }

    private get patho() {
        const data: any = {};
        data.pathologyNo = this.pathoForm.value.pathoNo;
        data.tissueReportDate = this.temp['tissueReportDate']; // this.pathoForm.value.tissueReportDate;
        data.resectionBiopsySite = this.pathoForm.value.resectionBiopsySite;
        data.status = this.pathoForm.value.status;
        data.id = this.pathoForm.value.id;

        if (!this.util.isNullOrEmpty(this.pathoForm.value.morphologyCode)) {
            data.morphology = {};
            data.morphology.code = this.pathoForm.value.morphologyCode;
            data.morphology.name = this.pathoForm.value.morphologyDesc;
        }

        if (!this.util.isNullOrEmpty(this.pathoForm.value.tissueProcedureCode)) {
            data.tissueProcedure = {};
            data.tissueProcedure.code = this.pathoForm.value.tissueProcedureCode;
        }

        if (!this.util.isNullOrEmpty(this.pathoForm.value.tissueTypeCode)) {
            data.tissueType = {};
            data.tissueType.code = this.pathoForm.value.tissueTypeCode;
        }

        if (!this.util.isNullOrEmpty(this.pathoForm.value.histologicGradeCode)) {
            data.histologicGrade = {};
            data.histologicGrade.code = this.pathoForm.value.histologicGradeCode;
        }

        return data;
    }

    private onScriptLoad() {
        const self = this;

        $(function() {
            $("#txtPathoNo").keypress(function (e) {
                const charCode = (e.which) ? e.which : e.keyCode;
                if (charCode > 31 && (charCode < 48 || charCode > 57)) {
                    return false;
                }

                const s = e.target.value;
                if (s && s.length === 2) {
                    $(this).val(`${s}-`);
                }
            }).blur(function() {
                const s = $(this).val();
                const pathoNo = self.util.formatRefNo(s);
                $(this).val(pathoNo);
            });
        });
    }

    public checkDuplicatePathoNo(): void {
        this.clearInputError('pathoNo');

        if (!this.util.isNullOrEmpty(this.pathoForm.value.pathoNo)) {
            const req: any = {};
            req.pathologyNo = this.pathoForm.value.pathoNo;

            if (!this.util.isNullOrEmpty(this.pathoForm.value.id)) {
                req.id = this.pathoForm.value.id;
            }

            this.dataService
                .connect(this.constants.CTX.SECURITY, this.constants.SERVICE_NAME.FIND_DUPLICATE_PATHO_NO, req)
                .then((res: any) => {
                    console.log("---> res: ", res);

                    if (res.responseStatus.responseCode !== this.msg.SUCCESS.code) {

                        if (res.responseStatus.responseCode === this.msg.ERROR_DUPLICATED.code) {
                            this.addInputError('pathoNo', {'duplicated': true});
                        }

                        this.modalConfig = { title: 'Message', message: res.responseStatus.responseMessage };
                        this.modalService.show(AlertDialogComponent, 'modal-sm', this.modalConfig);
                    }
                });
        }
    }

    private clearInputError(inputName: string): void {
        this.util.clearInputError(this.pathoForm, inputName);
    }

    private addInputError(inputName: string, errors: any): void {
        this.util.addInputError(this.pathoForm, inputName, errors);
    }

    public isDateInvalid(inputName: string) {
        this.clearInputError(inputName);
        const dt = this.pathoForm.value[inputName];

        if (this.util.isNullOrEmpty(dt) || dt.toString() === 'Invalid Date') {
            this.clearInputValue(inputName);
        } else {
            const year = Number(dt.getFullYear());
            if (year < 1900) {
                this.addInputError(inputName, {'invalidDate': true});
            }
        }
    }

    public bsValueChange(key: string, val: Date) {
        if (val) {
            this.temp[key] = new Date(val.getTime() - val.getTimezoneOffset() * 60 * 1000);
        } else {
            this.temp[key] = undefined;
        }
    }

    private clearInputValue(inputName: string): void {
        const control: any = {};
        control[inputName] = null;
        this.pathoForm.patchValue(control);
    }
}
