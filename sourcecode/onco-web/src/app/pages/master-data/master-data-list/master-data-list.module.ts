import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MasterDataListComponent } from "./master-data-list.component";
import { MasterDataListRoutingModule } from "./master-data-list-routing.module";
import { TranslateModule } from '@ngx-translate/core';
import { BreadcrumbModule } from "../../../shared/components/breadcrumb/breadcrumb.module";

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild(),
        MasterDataListRoutingModule,
        BreadcrumbModule
    ],
    declarations: [
        MasterDataListComponent
    ]
})
export class MasterDataListModule {}
