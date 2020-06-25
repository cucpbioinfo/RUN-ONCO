import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { BiospecimenSearchComponent } from "./biospecimen-search.component";

const routes: Routes = [
    {
        path: '',
        component: BiospecimenSearchComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class BiospecimenSearchRoutingModule {}
