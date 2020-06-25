import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { ViewIcdOComponent } from "./view-icd-o.component";
import { TranslateModule } from "@ngx-translate/core";
import { SharedServicesModule } from '../../../shared/services/shared-services.module';
import { SharedPipesModule } from '../../../shared/pipes/shared-pipes.module';
import { ViewIcdORoutingModule } from './view-icd-o-routing.module';
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
        ViewIcdORoutingModule,
        BreadcrumbModule,
        BsDatepickerModule,
        ModalModule,
        DataTableModule,
        RouterModule
    ],
    declarations: [
        ViewIcdOComponent
    ],
    exports: [
        ViewIcdOComponent
    ]
})
export class ViewIcdOModule {}
