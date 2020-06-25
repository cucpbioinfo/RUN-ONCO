import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { TranslateModule } from "@ngx-translate/core";

import { PagesRoutingModule } from "./pages-routing.module";
import { PagesComponent } from "./pages.component";
import { LayoutModule } from "../shared/components/layout/layout.module";

@NgModule({
    imports: [
        CommonModule,
        PagesRoutingModule,
        TranslateModule.forChild(),
        LayoutModule
    ],
    declarations: [PagesComponent]
})
export class PagesModule {}
