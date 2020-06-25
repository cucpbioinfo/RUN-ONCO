import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { MasterDataRoutingModule } from "./master-data-routing.module";
import { MasterDataComponent } from "./master-data.component";

@NgModule({
    imports: [
        CommonModule,
        MasterDataRoutingModule
    ],
    declarations: [
        MasterDataComponent
    ]
})
export class MasterDataModule {}
