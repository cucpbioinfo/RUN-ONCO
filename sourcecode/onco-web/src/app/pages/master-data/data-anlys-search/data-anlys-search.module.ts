import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { DataAnlysSearchComponent } from "./data-anlys-search.component";
import { DataAnlysSearchRoutingModule } from "./data-anlys-search-routing.module";

@NgModule({
    imports: [
        CommonModule,
        DataAnlysSearchRoutingModule
    ],
    declarations: [
        DataAnlysSearchComponent
    ]
})
export class DataAnlysSearchModule {}
