import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { CrfComponent } from './crf.component';
import { CrfStep1Component } from './crf-step1.component';
import { CrfStep2Component } from './crf-step2.component';
import { AuthGuard } from '../../../shared/services/auth-guard.service';

const routes: Routes = [
    {
        path: '', component: CrfComponent,
        canActivate: [AuthGuard],
        children: [
            { path: '', redirectTo: 'step1', pathMatch: 'full' },
            { path: 'step1', component: CrfStep1Component, data: { menuCode: 'CRF.STEP1' } },
            { path: 'step2', component: CrfStep2Component, data: { menuCode: 'CRF.STEP2' } }
        ],
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class CrfRoutingModule {
}
