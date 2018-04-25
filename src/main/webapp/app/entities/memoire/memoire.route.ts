import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MemoireComponent } from './memoire.component';
import { MemoireDetailComponent } from './memoire-detail.component';
import { MemoirePopupComponent } from './memoire-dialog.component';
import { MemoireDeletePopupComponent } from './memoire-delete-dialog.component';

export const memoireRoute: Routes = [
    {
        path: 'memoire',
        component: MemoireComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Memoires'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'memoire/:id',
        component: MemoireDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Memoires'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const memoirePopupRoute: Routes = [
    {
        path: 'memoire-new',
        component: MemoirePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Memoires'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'memoire/:id/edit',
        component: MemoirePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Memoires'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'memoire/:id/delete',
        component: MemoireDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Memoires'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
