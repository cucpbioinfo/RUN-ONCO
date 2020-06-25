import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';
import { PagesComponent } from './pages.component';
import { AuthGuard } from '../shared/services/auth-guard.service';

const routes: Routes = [
    {
        path: '',
        component: PagesComponent,
        children: [
            { path: '', redirectTo: 'patient-search', pathMatch: 'prefix', canActivate: [AuthGuard] },
            { path: 'patient-search', loadChildren: './patient/patient-search/patient-search.module#PatientSearchModule', canActivate: [AuthGuard] },
            { path: 'patient-form', loadChildren: './patient/patient-form/patient-form.module#PatientFormModule', canActivate: [AuthGuard] },
            { path: 'clinical-data-search', loadChildren: './clinical-data/clinical-data-search/clinical-data-search.module#ClinicalDataSearchModule', canActivate: [AuthGuard] },
            { path: 'clinical-record-form', loadChildren: './clinical-data/clinical-record-form/crf.module#CrfModule', canActivate: [AuthGuard] },
            { path: 'biospecimen-search', loadChildren: './biospecimen/biospecimen-search/biospecimen-search.module#BiospecimenSearchModule', canActivate: [AuthGuard] },
            { path: 'biospecimen-form', loadChildren: './biospecimen/biospecimen-form/biospecimen-form.module#BiospecimenFormModule', canActivate: [AuthGuard] },
            { path: 'view-variant-annotation', loadChildren: './variant-call/view-variant-annotation/view-variant-annotation.module#ViewVariantAnnotationModule', canActivate: [AuthGuard] },
            { path: 'view-rna-seq', loadChildren: './rna-seq/view-rna-seq/view-rna-seq.module#ViewRnaSeqModule', canActivate: [AuthGuard] },
            { path: 'patient-analysis', loadChildren: './patient/patient-analysis/patient-analysis.module#PatientAnalysisModule', canActivate: [AuthGuard] },
            { path: 'indiv-patient-analysis', loadChildren: './omics-analysis/indiv-patient-analysis/indiv-patient-analysis.module#IndivPatientAnalysisModule', canActivate: [AuthGuard] },
            { path: 'master-data', loadChildren: './master-data/master-data.module#MasterDataModule', canActivate: [AuthGuard] }
        ]
    }
];

@NgModule({
    imports: [RouterModule.forChild(routes)],
    exports: [RouterModule]
})
export class PagesRoutingModule {}
