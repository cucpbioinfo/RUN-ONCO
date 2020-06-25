import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { IndivPatientAnalysisComponent } from "./indiv-patient-analysis.component";

const routes: Routes = [
    {
        path: '',
        component: IndivPatientAnalysisComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class IndivPatientAnalysisRoutingModule {}
