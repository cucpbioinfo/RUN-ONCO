import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { IcdSearchComponent } from './icd-search.component';

const routes: Routes = [
    {
        path: '',
        component: IcdSearchComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class IcdSearchRoutingModule {}
