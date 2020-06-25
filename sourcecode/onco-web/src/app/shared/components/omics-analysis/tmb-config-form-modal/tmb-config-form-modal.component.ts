import { Component, OnInit, AfterViewInit } from "@angular/core";
import { BsModalRef } from "ngx-bootstrap/modal";
import { FormBuilder, FormGroup, FormControl } from "@angular/forms";
import { Subject } from "rxjs";
import { ValidationService } from "../../../services/validation.service";

@Component({
    selector: "app-tmb-config-form-modal",
    templateUrl: "./tmb-config-form-modal.component.html",
    styleUrls: ["./tmb-config-form-modal.component.scss"]
})
export class TmbConfigFormModalComponent implements OnInit, AfterViewInit {

    public submitted: boolean;
    public tmbForm: FormGroup;
    public onClose: Subject<any>;
    public selected: any;

    constructor(
        public fb: FormBuilder,
        public bsModalRef: BsModalRef
    ) {
        this.onClose = new Subject();
    }

    ngOnInit(): void {
        this.init();
    }

    ngAfterViewInit(): void {
        if (this.selected) {
            const values: any = {};
            values.exomeSize = this.selected.exomeSize;
            this.tmbForm.patchValue(values);
        }
    }

    private init(): void {
        this.buildForm();
    }

    private buildForm(): void {
        this.tmbForm = this.fb.group({
            exomeSize: new FormControl('', [ValidationService.requiredValidator]),
        });
    }

    public onSave() {
        this.submitted = true;

        if (this.tmbForm.valid) {
            this.onClose.next(this.config);
            this.bsModalRef.hide();
        }
    }

    private get config() {
        const data: any = {};
        data.exomeSize = Number(this.tmbForm.value.exomeSize);
        return data;
    }
}
