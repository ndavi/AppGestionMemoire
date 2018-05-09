import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { AppGestionMemoireSharedModule } from '../shared';

import { HOME_ROUTE, HomeComponent } from './';
import {HomeService} from "./home.service";

@NgModule({
    imports: [
        AppGestionMemoireSharedModule,
        RouterModule.forChild([ HOME_ROUTE ])
    ],
    declarations: [
        HomeComponent,
    ],
    entryComponents: [
    ],
    providers: [
        HomeService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppGestionMemoireHomeModule {}
