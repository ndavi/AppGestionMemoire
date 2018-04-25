import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { Secteur } from './secteur.model';
import { SecteurService } from './secteur.service';

@Component({
    selector: 'jhi-secteur-detail',
    templateUrl: './secteur-detail.component.html'
})
export class SecteurDetailComponent implements OnInit, OnDestroy {

    secteur: Secteur;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private secteurService: SecteurService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSecteurs();
    }

    load(id) {
        this.secteurService.find(id)
            .subscribe((secteurResponse: HttpResponse<Secteur>) => {
                this.secteur = secteurResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSecteurs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'secteurListModification',
            (response) => this.load(this.secteur.id)
        );
    }
}
