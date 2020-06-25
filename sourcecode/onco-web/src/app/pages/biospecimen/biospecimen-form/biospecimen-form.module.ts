import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BiospecimenFormRoutingModule } from "./biospecimen-form-routing.module";
import { BiospecimenFormComponent } from "./biospecimen-form.component";
import { SharedServicesModule } from '../../../shared/services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../shared/directives/shared-directives.module';
import { TranslateModule } from '@ngx-translate/core';
import { DataTableModule } from '../../../shared/components/data-table';
import { BsDatepickerModule } from "ngx-bootstrap/datepicker";
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { ViewPatientInfoModule } from "../../../shared/components/patient/view-patient-info/view-patient-info.module";
import { BreadcrumbModule } from "../../../shared/components/breadcrumb/breadcrumb.module";
import { PatientAddModule } from "../../../shared/components/patient/patient-add/patient-add.module";

@NgModule({
    imports: [
        CommonModule,
        BiospecimenFormRoutingModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        DataTableModule,
        BsDatepickerModule,
        TypeaheadModule,
        ViewPatientInfoModule,
        BreadcrumbModule,
        PatientAddModule
    ],
    declarations: [
        BiospecimenFormComponent
    ],
    exports: [
        BiospecimenFormComponent
    ]
})
export class BiospecimenFormModule { }
