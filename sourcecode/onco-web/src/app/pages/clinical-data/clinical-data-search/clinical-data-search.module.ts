import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClinicalDataSearchRoutingModule } from './clinical-data-search-routing.module';
import { ClinicalDataSearchComponent } from './clinical-data-search.component';
import { SharedServicesModule } from '../../../shared/services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../shared/directives/shared-directives.module';
import { TranslateModule } from '@ngx-translate/core';
import { DataTableModule } from '../../../shared/components/data-table';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { RouterModule } from "@angular/router";
import { ModalModule } from 'ngx-bootstrap/modal';
import { BreadcrumbModule } from "../../../shared/components/breadcrumb/breadcrumb.module";
import { CollapseModule } from 'ngx-bootstrap/collapse';

@NgModule({
    imports: [
        CommonModule,
        ClinicalDataSearchRoutingModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        DataTableModule,
        TypeaheadModule,
        RouterModule,
        ModalModule,
        BreadcrumbModule,
        CollapseModule
    ],
    declarations: [
        ClinicalDataSearchComponent
    ],
    exports: [
        ClinicalDataSearchComponent
    ]
})
export class ClinicalDataSearchModule { }
