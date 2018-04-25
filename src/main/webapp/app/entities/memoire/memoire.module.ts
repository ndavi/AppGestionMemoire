import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppGestionMemoireSharedModule } from '../../shared';
import {
    MemoireService,
    MemoirePopupService,
    MemoireComponent,
    MemoireDetailComponent,
    MemoireDialogComponent,
    MemoirePopupComponent,
    MemoireDeletePopupComponent,
    MemoireDeleteDialogComponent,
    memoireRoute,
    memoirePopupRoute,
} from './';

const ENTITY_STATES = [
    ...memoireRoute,
    ...memoirePopupRoute,
];

@NgModule({
    imports: [
        AppGestionMemoireSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MemoireComponent,
        MemoireDetailComponent,
        MemoireDialogComponent,
        MemoireDeleteDialogComponent,
        MemoirePopupComponent,
        MemoireDeletePopupComponent,
    ],
    entryComponents: [
        MemoireComponent,
        MemoireDialogComponent,
        MemoirePopupComponent,
        MemoireDeleteDialogComponent,
        MemoireDeletePopupComponent,
    ],
    providers: [
        MemoireService,
        MemoirePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppGestionMemoireMemoireModule {}
