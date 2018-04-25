import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppGestionMemoireSharedModule } from '../../shared';
import {
    PromotionService,
    PromotionPopupService,
    PromotionComponent,
    PromotionDetailComponent,
    PromotionDialogComponent,
    PromotionPopupComponent,
    PromotionDeletePopupComponent,
    PromotionDeleteDialogComponent,
    promotionRoute,
    promotionPopupRoute,
} from './';

const ENTITY_STATES = [
    ...promotionRoute,
    ...promotionPopupRoute,
];

@NgModule({
    imports: [
        AppGestionMemoireSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        PromotionComponent,
        PromotionDetailComponent,
        PromotionDialogComponent,
        PromotionDeleteDialogComponent,
        PromotionPopupComponent,
        PromotionDeletePopupComponent,
    ],
    entryComponents: [
        PromotionComponent,
        PromotionDialogComponent,
        PromotionPopupComponent,
        PromotionDeleteDialogComponent,
        PromotionDeletePopupComponent,
    ],
    providers: [
        PromotionService,
        PromotionPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppGestionMemoirePromotionModule {}
