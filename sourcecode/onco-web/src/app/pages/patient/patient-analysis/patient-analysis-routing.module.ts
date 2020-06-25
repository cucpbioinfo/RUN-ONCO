import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PatientAnalysisComponent } from "./patient-analysis.component";

const routes: Routes = [
    {
        path: '',
        component: PatientAnalysisComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PatientAnalysisRoutingModule {}
