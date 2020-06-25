import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { ViewRnaSeqComponent } from './view-rna-seq.component';

const routes: Routes = [
    {
        path: '',
        component: ViewRnaSeqComponent
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class ViewRnaSeqRoutingModule {}
