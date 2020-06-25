import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { IcdOSearchComponent } from './icd-o-search.component';

const routes: Routes = [
    {
        path: '',
        component: IcdOSearchComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class IcdOSearchRoutingModule {}
