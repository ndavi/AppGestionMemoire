import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AppGestionMemoireSpecialiteModule } from './specialite/specialite.module';
import { AppGestionMemoireUserExtraModule } from './user-extra/user-extra.module';
import { AppGestionMemoirePromotionModule } from './promotion/promotion.module';
import { AppGestionMemoireSecteurModule } from './secteur/secteur.module';
import { AppGestionMemoireTagModule } from './tag/tag.module';
import { AppGestionMemoireMemoireModule } from './memoire/memoire.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        AppGestionMemoireSpecialiteModule,
        AppGestionMemoireUserExtraModule,
        AppGestionMemoirePromotionModule,
        AppGestionMemoireSecteurModule,
        AppGestionMemoireTagModule,
        AppGestionMemoireMemoireModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AppGestionMemoireEntityModule {}
