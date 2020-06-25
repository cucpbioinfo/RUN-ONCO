import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ViewIcdComponent } from "./view-icd.component";
import { TranslateModule } from "@ngx-translate/core";
import { SharedServicesModule } from '../../../shared/services/shared-services.module';
import { SharedPipesModule } from '../../../shared/pipes/shared-pipes.module';
import { ViewIcdRoutingModule } from './view-icd-routing.module';
import { BreadcrumbModule } from "../../../shared/components/breadcrumb/breadcrumb.module";
import { BsDatepickerModule } from "ngx-bootstrap/datepicker";
import { ModalModule } from 'ngx-bootstrap/modal';
import { DataTableModule } from '../../../shared/components/data-table';
import { RouterModule } from "@angular/router";

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild(),
        SharedPipesModule,
        SharedServicesModule,
        ViewIcdRoutingModule,
        BreadcrumbModule,
        BsDatepickerModule,
        ModalModule,
        DataTableModule,
        RouterModule
    ],
    declarations: [
        ViewIcdComponent
    ],
    exports: [
        ViewIcdComponent
    ]
})
export class ViewIcdModule {}
