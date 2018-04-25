import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppGestionMemoireSharedModule } from '../../shared';
import {
    SecteurService,
    SecteurPopupService,
    SecteurComponent,
    SecteurDetailComponent,
    SecteurDialogComponent,
    SecteurPopupComponent,
    SecteurDeletePopupComponent,
    SecteurDeleteDialogComponent,
    secteurRoute,
    secteurPopupRoute,
} from './';

const ENTITY_STATES = [
    ...secteurRoute,
    ...secteurPopupRoute,
];

@NgModule({
    imports: [
        AppGestionMemoireSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        SecteurComponent,
        SecteurDetailComponent,
        SecteurDialogComponent,
        SecteurDeleteDialogComponent,
        SecteurPopupComponent,
        SecteurDeletePopupComponent,
    ],
    entryComponents: [
        SecteurComponent,
        SecteurDialogComponent,
        SecteurPopupComponent,
        SecteurDeleteDialogComponent,
        SecteurDeletePopupComponent,
    ],
    providers: [
        SecteurService,
        SecteurPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppGestionMemoireSecteurModule {}
