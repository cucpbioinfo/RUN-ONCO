import { BrowserModule } from "@angular/platform-browser";
import { NgModule } from "@angular/core";
import { CommonModule } from "@angular/common";
import { HttpClientModule, HttpClient } from "@angular/common/http";
import { TranslateModule, TranslateLoader } from "@ngx-translate/core";
import { TranslateHttpLoader } from "@ngx-translate/http-loader";
import { Http } from "@angular/http";
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';

import { AppRoutingModule } from "./app-routing.module";
import { AuthGuard } from "./shared/services/auth-guard.service";
import { AppComponent } from "./app.component";
import { SharedServicesModule } from "./shared/services/shared-services.module";
import { LayoutModule } from "./shared/components/layout/layout.module";
import { AuthenticatedHttpService } from "./shared/services/auth-http.service";
import { ModalModule } from 'ngx-bootstrap/modal';
import { ConfirmDialogModule } from "./shared/components/modal-dialog/confirm-dialog/confirm-dialog.module";
import { AlertDialogModule } from "./shared/components/modal-dialog/alert-dialog/alert-dialog.module";
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { TypeaheadModule } from 'ngx-bootstrap/typeahead';
import { PathoFormModalModule } from './shared/components/app-modal/clinical-data/patho-form-modal/patho-form-modal.module';
import { ClinicalCourseFormModalModule } from './shared/components/app-modal/clinical-data/clinical-course-form-modal/clinical-course-form-modal.module';
import { BsDatepickerModule } from "ngx-bootstrap/datepicker";
import { SurvivalFollowupFormModalModule } from "./shared/components/app-modal/clinical-data/survival-followup-form-modal/survival-followup-form-modal.module";
import { VariantCallFormModalModule } from './shared/components/app-modal/variant-call/variant-call-form-modal/variant-call-form-modal.module';
import { ActionableVariantViewModalModule } from './shared/components/app-modal/variant-call/actionable-variant-view-modal/actionable-variant-view-modal.module';
import { RnaSeqFormModalModule } from './shared/components/app-modal/rna-seq/rna-seq-form-modal/rna-seq-form-modal.module';
import { CollapseModule } from 'ngx-bootstrap/collapse';
import { ViewImageDialogModule } from "./shared/components/modal-dialog/view-image-dialog/view-image-dialog.module";
import { ClusterConfigFormModalModule } from "./shared/components/omics-analysis/cluster-config-form-modal/cluster-config-form-modal.module";
import { PathoViewModalModule } from "./shared/components/app-modal/clinical-data/patho-view-modal/patho-view-modal.module";
import { IcdFormModalModule } from "./shared/components/app-modal/master-data/icd-form-modal/icd-form-modal.module";
import { IcdOFormModalModule } from "./shared/components/app-modal/master-data/icd-o-form-modal/icd-o-form-modal.module";
import { DynamicContentModalModule } from "./shared/components/app-modal/master-data/dynamic-content-modal/dynamic-content-modal.module";
import { TmbConfigFormModalModule } from "./shared/components/omics-analysis/tmb-config-form-modal/tmb-config-form-modal.module";
import { UserFormModalModule } from "./shared/components/app-modal/master-data/user-form-modal/user-form-modal.module";

// AoT requires an exported function for factories
export function createTranslateLoader(http: HttpClient) {
    // for development
    // return new TranslateHttpLoader(http, '/start-angular/onco-web/master/dist/assets/i18n/', '.json');
    return new TranslateHttpLoader(http, "./assets/i18n/", ".json");
}

@NgModule({
    imports: [
        CommonModule,
        BrowserModule,
        AppRoutingModule,
        HttpClientModule,
        BrowserAnimationsModule,
        TranslateModule.forRoot({
            loader: {
                provide: TranslateLoader,
                useFactory: createTranslateLoader,
                deps: [HttpClient]
            }
        }),
        SharedServicesModule.forRoot(),
        LayoutModule,
        ConfirmDialogModule,
        AlertDialogModule,
        PathoFormModalModule,
        ClinicalCourseFormModalModule,
        ModalModule.forRoot(),
        BsDropdownModule.forRoot(),
        TypeaheadModule.forRoot(),
        BsDatepickerModule.forRoot(),
        SurvivalFollowupFormModalModule,
        VariantCallFormModalModule,
        ActionableVariantViewModalModule,
        RnaSeqFormModalModule,
        CollapseModule.forRoot(),
        ViewImageDialogModule,
        ClusterConfigFormModalModule,
        PathoViewModalModule,
        IcdFormModalModule,
        IcdOFormModalModule,
        DynamicContentModalModule,
        TmbConfigFormModalModule,
        UserFormModalModule
    ],
    declarations: [AppComponent],
    providers: [
        AuthGuard,
        { provide: Http, useClass: AuthenticatedHttpService }
    ],
    bootstrap: [AppComponent]
})
export class AppModule {}
