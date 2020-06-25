import { NgModule, ModuleWithProviders } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ClustergrammerComponent } from './clustergrammer.component';
import { TranslateModule } from '@ngx-translate/core';

@NgModule({
    imports: [
        CommonModule,
        TranslateModule.forChild()
    ],
    declarations: [
        ClustergrammerComponent
    ],
    exports: [
        ClustergrammerComponent
    ]
})
export class ClustergrammerModule {
    static forRoot(): ModuleWithProviders {
        return {
            ngModule: ClustergrammerModule,
            providers: [

            ]
        };
    }
}
