import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { SecteurComponent } from './secteur.component';
import { SecteurDetailComponent } from './secteur-detail.component';
import { SecteurPopupComponent } from './secteur-dialog.component';
import { SecteurDeletePopupComponent } from './secteur-delete-dialog.component';

export const secteurRoute: Routes = [
    {
        path: 'secteur',
        component: SecteurComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Secteurs'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'secteur/:id',
        component: SecteurDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Secteurs'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const secteurPopupRoute: Routes = [
    {
        path: 'secteur-new',
        component: SecteurPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Secteurs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'secteur/:id/edit',
        component: SecteurPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Secteurs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'secteur/:id/delete',
        component: SecteurDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Secteurs'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
