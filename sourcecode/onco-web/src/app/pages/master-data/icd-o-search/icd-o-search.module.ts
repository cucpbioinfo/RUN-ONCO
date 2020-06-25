import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { IcdOSearchComponent } from "./icd-o-search.component";
import { IcdOSearchRoutingModule } from "./icd-o-search-routing.module";
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
import { SharedPipesModule } from "../../../shared/pipes/shared-pipes.module";

@NgModule({
    imports: [
        CommonModule,
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
        CollapseModule,
        IcdOSearchRoutingModule,
        SharedPipesModule
    ],
    declarations: [
        IcdOSearchComponent
    ],
    exports: [
        IcdOSearchComponent
    ]
})
export class IcdOSearchModule {}
