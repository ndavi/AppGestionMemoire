import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PromotionComponent } from './promotion.component';
import { PromotionDetailComponent } from './promotion-detail.component';
import { PromotionPopupComponent } from './promotion-dialog.component';
import { PromotionDeletePopupComponent } from './promotion-delete-dialog.component';

export const promotionRoute: Routes = [
    {
        path: 'promotion',
        component: PromotionComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Promotions'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'promotion/:id',
        component: PromotionDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Promotions'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const promotionPopupRoute: Routes = [
    {
        path: 'promotion-new',
        component: PromotionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Promotions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'promotion/:id/edit',
        component: PromotionPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Promotions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'promotion/:id/delete',
        component: PromotionDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Promotions'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
