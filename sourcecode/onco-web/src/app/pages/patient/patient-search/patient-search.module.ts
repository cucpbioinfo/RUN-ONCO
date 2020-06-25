import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { PatientSearchRoutingModule } from './patient-search-routing.module';
import { PatientSearchComponent } from './patient-search.component';
import { SharedServicesModule } from '../../../shared/services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../shared/directives/shared-directives.module';
import { TranslateModule } from "@ngx-translate/core";
import { DataTableModule } from '../../../shared/components/data-table';
import { BreadcrumbModule } from "../../../shared/components/breadcrumb/breadcrumb.module";
import { CollapseModule } from 'ngx-bootstrap/collapse';

@NgModule({
    imports: [
        CommonModule,
        PatientSearchRoutingModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        DataTableModule,
        BreadcrumbModule,
        CollapseModule
    ],
    declarations: [
        PatientSearchComponent
    ]
})
export class PatientSearchModule { }
