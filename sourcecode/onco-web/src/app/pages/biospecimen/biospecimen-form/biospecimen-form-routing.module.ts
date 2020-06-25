import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BiospecimenFormComponent } from "./biospecimen-form.component";

const routes: Routes = [
    {
        path: '',
        component: BiospecimenFormComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BiospecimenFormRoutingModule {}
