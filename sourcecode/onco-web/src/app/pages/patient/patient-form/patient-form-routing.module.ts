import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PatientFormComponent } from "./patient-form.component";

const routes: Routes = [
    {
        path: '',
        component: PatientFormComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PatientFormRoutingModule {}
