import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppGestionMemoireSharedModule } from '../../shared';
import {
    SpecialiteService,
    SpecialitePopupService,
    SpecialiteComponent,
    SpecialiteDetailComponent,
    SpecialiteDialogComponent,
    SpecialitePopupComponent,
    SpecialiteDeletePopupComponent,
    SpecialiteDeleteDialogComponent,
    specialiteRoute,
    specialitePopupRoute,
} from './';

const ENTITY_STATES = [
    ...specialiteRoute,
    ...specialitePopupRoute,
];

@NgModule({
    imports: [
        AppGestionMemoireSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SpecialiteComponent,
        SpecialiteDetailComponent,
        SpecialiteDialogComponent,
        SpecialiteDeleteDialogComponent,
        SpecialitePopupComponent,
        SpecialiteDeletePopupComponent,
    ],
    entryComponents: [
        SpecialiteComponent,
        SpecialiteDialogComponent,
        SpecialitePopupComponent,
        SpecialiteDeleteDialogComponent,
        SpecialiteDeletePopupComponent,
    ],
    providers: [
        SpecialiteService,
        SpecialitePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppGestionMemoireSpecialiteModule {}
