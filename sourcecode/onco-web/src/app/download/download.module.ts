import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { DownloadRoutingModule } from './download-routing.module';
import { DownloadComponent } from './download.component';
import { SharedServicesModule } from '../shared/services/shared-services.module';
import { ReactiveFormsModule, FormsModule } from '@angular/forms';
import { SharedDirectivesModule } from '../shared/directives/shared-directives.module';
import { LoginFormModule } from '../shared/components/login-form/login-form.module';
import { LayoutModule } from '../shared/components/layout/layout.module';

@NgModule({
    imports: [
        CommonModule,
        DownloadRoutingModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        LoginFormModule,
        LayoutModule
    ],
    declarations: [
        DownloadComponent
    ]
})
export class DownloadModule { }
