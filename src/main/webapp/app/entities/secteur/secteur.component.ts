import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Secteur } from './secteur.model';
import { SecteurService } from './secteur.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-secteur',
    templateUrl: './secteur.component.html'
})
export class SecteurComponent implements OnInit, OnDestroy {
secteurs: Secteur[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private secteurService: SecteurService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.secteurService.query().subscribe(
            (res: HttpResponse<Secteur[]>) => {
                this.secteurs = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInSecteurs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: Secteur) {
        return item.id;
    }
    registerChangeInSecteurs() {
        this.eventSubscriber = this.eventManager.subscribe('secteurListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
