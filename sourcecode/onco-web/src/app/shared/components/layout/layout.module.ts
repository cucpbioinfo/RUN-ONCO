import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { TranslateModule } from '@ngx-translate/core';
import { RouterModule } from '@angular/router';
import { SharedServicesModule } from '../../services/shared-services.module';
import { HeaderComponent } from '../layout/header/header.component';
import { FooterComponent } from '../layout/footer/footer.component';
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild(),
        RouterModule,
        SharedServicesModule,
        ReactiveFormsModule,
        FormsModule,
        TypeaheadModule
    ],
    declarations: [
        HeaderComponent,
        FooterComponent
    ],
    exports: [
        HeaderComponent,
        FooterComponent
    ]
})
export class LayoutModule { }
