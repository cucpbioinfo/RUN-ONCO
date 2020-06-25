import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { DynamicContentSearchComponent } from './dynamic-content-search.component';

const routes: Routes = [
    {
        path: '',
        component: DynamicContentSearchComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class DynamicContentSearchRoutingModule {}
