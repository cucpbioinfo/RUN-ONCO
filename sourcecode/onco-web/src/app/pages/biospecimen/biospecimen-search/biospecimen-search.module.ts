import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { BiospecimenSearchRoutingModule } from "./biospecimen-search-routing.module";
import { BiospecimenSearchComponent } from "./biospecimen-search.component";
import { SharedServicesModule } from '../../../shared/services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../shared/directives/shared-directives.module';
import { TranslateModule } from '@ngx-translate/core';
import { DataTableModule } from '../../../shared/components/data-table';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { BreadcrumbModule } from "../../../shared/components/breadcrumb/breadcrumb.module";
import { CollapseModule } from 'ngx-bootstrap/collapse';

@NgModule({
    imports: [
        CommonModule,
        BiospecimenSearchRoutingModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        TranslateModule.forChild(),
        DataTableModule,
        TypeaheadModule,
        BreadcrumbModule,
        CollapseModule
    ],
    declarations: [
        BiospecimenSearchComponent
    ],
    exports: [
        BiospecimenSearchComponent
    ]
})
export class BiospecimenSearchModule { }
