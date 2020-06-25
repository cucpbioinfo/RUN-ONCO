import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { MasterDataComponent } from "./master-data.component";
import { AuthGuard } from '../../shared/services/auth-guard.service';

const routes: Routes = [
    {
        path: '',
        component: MasterDataComponent,
        children: [
            { path: '', redirectTo: 'list', pathMatch: 'prefix', canActivate: [AuthGuard] },
            { path: 'list', loadChildren: './master-data-list/master-data-list.module#MasterDataListModule', canActivate: [AuthGuard] },
            { path: 'icd-search', loadChildren: './icd-search/icd-search.module#IcdSearchModule', canActivate: [AuthGuard] },
            { path: 'icd-o-search', loadChildren: './icd-o-search/icd-o-search.module#IcdOSearchModule', canActivate: [AuthGuard] },
            { path: 'view-icd', loadChildren: './view-icd/view-icd.module#ViewIcdModule', canActivate: [AuthGuard] },
            { path: 'view-icd-o', loadChildren: './view-icd-o/view-icd-o.module#ViewIcdOModule', canActivate: [AuthGuard] },
            { path: 'dynamic-content-search', loadChildren: './dynamic-content-search/dynamic-content-search.module#DynamicContentSearchModule', canActivate: [AuthGuard] },
            { path: 'data-anlys-search', loadChildren: './data-anlys-search/data-anlys-search.module#DataAnlysSearchModule', canActivate: [AuthGuard] },
            { path: 'user-search', loadChildren: './user-search/user-search.module#UserSearchModule', canActivate: [AuthGuard] }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class MasterDataRoutingModule {}
