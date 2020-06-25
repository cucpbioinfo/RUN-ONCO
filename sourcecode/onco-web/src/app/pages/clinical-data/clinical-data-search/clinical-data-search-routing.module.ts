import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ClinicalDataSearchComponent } from './clinical-data-search.component';

const routes: Routes = [
    {
        path: '',
        component: ClinicalDataSearchComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ClinicalDataSearchRoutingModule {}
