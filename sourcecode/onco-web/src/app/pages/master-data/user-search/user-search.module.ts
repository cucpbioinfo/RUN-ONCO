import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { BreadcrumbModule } from "../../../shared/components/breadcrumb/breadcrumb.module";
import { UserSearchComponent } from "./user-search.component";
import { UserSearchRoutingModule } from "./user-search-routing.module";
import { SharedServicesModule } from '../../../shared/services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../../../shared/directives/shared-directives.module';
import { TranslateModule } from '@ngx-translate/core';
import { DataTableModule } from '../../../shared/components/data-table';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { RouterModule } from "@angular/router";
import { ModalModule } from 'ngx-bootstrap/modal';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { SharedPipesModule } from "../../../shared/pipes/shared-pipes.module";

@NgModule({
    imports: [
        CommonModule,
        BreadcrumbModule,
        UserSearchRoutingModule,
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
        SharedPipesModule
    ],
    declarations: [
        UserSearchComponent
    ],
    exports: [
        UserSearchComponent
    ]
})
export class UserSearchModule {}
