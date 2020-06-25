import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DataAnlysSearchComponent } from './data-anlys-search.component';

const routes: Routes = [
    {
        path: '',
        component: DataAnlysSearchComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DataAnlysSearchRoutingModule {}
