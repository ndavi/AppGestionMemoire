import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SpecialiteComponent } from './specialite.component';
import { SpecialiteDetailComponent } from './specialite-detail.component';
import { SpecialitePopupComponent } from './specialite-dialog.component';
import { SpecialiteDeletePopupComponent } from './specialite-delete-dialog.component';

export const specialiteRoute: Routes = [
    {
        path: 'specialite',
        component: SpecialiteComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Specialites'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'specialite/:id',
        component: SpecialiteDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Specialites'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const specialitePopupRoute: Routes = [
    {
        path: 'specialite-new',
        component: SpecialitePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Specialites'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'specialite/:id/edit',
        component: SpecialitePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Specialites'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'specialite/:id/delete',
        component: SpecialiteDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Specialites'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
