import { NgModule, ModuleWithProviders } from "@angular/core";
import { CommonModule } from "@angular/common";
import { LoginFormComponent } from "./login-form.component";
import { SharedServicesModule } from "../../services/shared-services.module";
import { ReactiveFormsModule, FormsModule } from "@angular/forms";
import { SharedDirectivesModule } from "../../directives/shared-directives.module";
import { RouterModule } from '@angular/router';

@NgModule({
    imports: [
        CommonModule,
        SharedServicesModule,
        FormsModule,
        ReactiveFormsModule,
        SharedDirectivesModule,
        RouterModule
    ],
    declarations: [
        LoginFormComponent
    ],
    exports: [
        LoginFormComponent
    ]
})
export class LoginFormModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: LoginFormModule,
            providers: [

            ]
        };
    }
}
